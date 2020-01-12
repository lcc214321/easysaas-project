package org.easymis.easysaas.member.service.impl;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.easymis.easysaas.common.cache.RedisPrefixConstant;
import org.easymis.easysaas.common.cache.RedisUtils;
import org.easymis.easysaas.common.result.RestResult;
import org.easymis.easysaas.common.sms.AliyunCommonRpc;
import org.easymis.easysaas.common.sms.AliyunSmsResult;
import org.easymis.easysaas.common.sms.SmsUtil;
import org.easymis.easysaas.common.utils.NameBuilder;
import org.easymis.easysaas.member.config.datasource.DataSourceType;
import org.easymis.easysaas.member.config.datasource.EasymisDataSource;
import org.easymis.easysaas.member.entitys.mybatis.dto.Member;
import org.easymis.easysaas.member.entitys.mybatis.dto.SendSms;
import org.easymis.easysaas.member.entitys.mybatis.mapper.SendSmsMapper;
import org.easymis.easysaas.member.entitys.mybatis.mapper.UserMapper;
import org.easymis.easysaas.member.security.check.LoginWrongChecker;
import org.easymis.easysaas.member.security.userdetail.User;
import org.easymis.easysaas.member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    private LoginWrongChecker checker = new LoginWrongChecker();
	@Autowired
	UserMapper mapper;
	@Autowired
	SendSmsMapper sendSmsMapper;

	@EasymisDataSource(DataSourceType.Master)
	public Member findByMobile(String mobile) {
		return mapper.findByPhoneNumber(mobile);
	}

	@EasymisDataSource(DataSourceType.Master)
	public RestResult getRegisterShortMessage(String mobile) {
        Integer count = sendSmsMapper.getCount(mobile, 1);
        if(count>5) {
            return RestResult.buildFail("发送短信过于频繁,请稍后再试");
        }else {
            String code = SmsUtil.smsCode();
            AliyunSmsResult result = AliyunCommonRpc.sendSms(mobile, code);
            if (!Objects.equals(result.getCode(), "OK")) {
                return RestResult.buildFail("发送短信过于频繁,请稍后再试");
            }
            redisTemplate.opsForValue().set(RedisUtils.joinKey(RedisPrefixConstant.USER_REG_SMS, mobile), code, 5, TimeUnit.MINUTES); 
            SendSms bean= new SendSms();
            bean.setId(UUID.randomUUID().toString());
            bean.setCode(code);
            bean.setMobile(mobile);
            bean.setSendType(1);
            bean.setSendTime(LocalDateTime.now());
            sendSmsMapper.save(bean);
        }


        return RestResult.buildSuccess();

    }

	@EasymisDataSource(DataSourceType.Master)
	public RestResult getLoginShortMessage(String mobile) {
        Integer count = sendSmsMapper.getCount(mobile, 2);
        if(count>5) {
            return RestResult.buildFail("发送短信过于频繁,请稍后再试");
        }else {
            String code = SmsUtil.smsCode();
            AliyunSmsResult result = AliyunCommonRpc.sendSms(mobile, code);
            if (!Objects.equals(result.getCode(), "OK")) {
                return RestResult.buildFail("发送短信过于频繁,请稍后再试");
            }
            redisTemplate.opsForValue().set(RedisUtils.joinKey(RedisPrefixConstant.USER_LOGIN_SMS, mobile), code, 5, TimeUnit.MINUTES); 
            SendSms bean= new SendSms();
            bean.setId(UUID.randomUUID().toString());
            bean.setCode(code);
            bean.setMobile(mobile);
            bean.setSendType(2);
            bean.setSendTime(LocalDateTime.now());
            sendSmsMapper.save(bean);
        }
        return RestResult.buildSuccess();

    }

	@EasymisDataSource(DataSourceType.Master)
	public RestResult getForgitPasswordShortMessage(String mobile) {
        Integer count = sendSmsMapper.getCount(mobile, 3);
        if(count>5) {
            return RestResult.buildFail("发送短信过于频繁,请稍后再试");
        }else {
            String code = SmsUtil.smsCode();
            AliyunSmsResult result = AliyunCommonRpc.sendSms(mobile, code);
            if (!Objects.equals(result.getCode(), "OK")) {
                return RestResult.buildFail("发送短信过于频繁,请稍后再试");
            }
            redisTemplate.opsForValue().set(RedisUtils.joinKey(RedisPrefixConstant.USER_FORGIT_PASSWORD_SMS, mobile), code, 5, TimeUnit.MINUTES); 
            SendSms bean= new SendSms();
            bean.setId(UUID.randomUUID().toString());
            bean.setCode(code);
            bean.setMobile(mobile);
            bean.setSendType(3);
            bean.setSendTime(LocalDateTime.now());
            sendSmsMapper.save(bean);
        }
        return RestResult.buildSuccess();
    }

	@EasymisDataSource(DataSourceType.Master)
	public RestResult quickRegister(String mobile, String code, String password) {
        String cacheCode = (String) redisTemplate.opsForValue().get(RedisUtils.joinKey(RedisPrefixConstant.USER_REG_SMS, mobile));
        if (!Objects.equals(cacheCode, code)) {
            return RestResult.buildFail("验证码错误!");
        } else {
            RestResult restResult = RestResult.buildSuccess();
            Member user = generateUser(mobile, password);
            user.setEnabled(true);
            try {
            	mapper.insertByBean(user);
            } catch (DuplicateKeyException e) {
                return RestResult.buildFail("该号码已经被注册!");
            }
            return restResult;
        }
    }
	@EasymisDataSource(DataSourceType.Master)
	public Member quickRegister(String phoneNumber) {
		Member user = new Member();
        user.setPhoneNumber(phoneNumber);
        //user.setPassword(Optional.ofNullable(password).orElse(generatePassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setSex("男");  //默认男
        user.setUserNo(generateUserNo(phoneNumber));
        //user.setName(generateUsername());
        user.setName(NameBuilder.build(4));
        user.setEnabled(true);
		mapper.insertByBean(user);
		return user;
	}
	@EasymisDataSource(DataSourceType.Master)
	public RestResult updatePasswordByOldPassword(String oldpwd, String newpwd, String phonwNumber) {
		Member user =findByMobile(phonwNumber);
        if (Objects.isNull(user)) {
            return RestResult.buildError("该号码未注册");
        }
        if (Objects.equals(oldpwd, user.getPassword())) {
        	user.setPassword(newpwd);
            updatePassword(user);
            //解除账号锁定
            checker.loginUnlock(redisTemplate,phonwNumber);
            return RestResult.buildSuccess();
        } else {
            return RestResult.buildFail("旧密码错误");
        }
    }

	@EasymisDataSource(DataSourceType.Master)
	public RestResult updatePasswordByShortMessage(String smscode, String newpwd, String phonwNumber) {
		Member user = mapper.findByPhoneNumber(phonwNumber);
        if (Objects.isNull(user)) {
            return RestResult.buildError("该号码未注册");
        }
        String cacheCode = (String) redisTemplate.opsForValue().get(RedisUtils.joinKey(RedisPrefixConstant.USER_FORGIT_PASSWORD_SMS, user.getPhoneNumber()));
        if (Objects.equals(cacheCode, smscode)) {
        	user.setPassword(newpwd);
            updatePassword(user);
            //解除账号锁定
            checker.loginUnlock(redisTemplate,phonwNumber);
            return RestResult.buildSuccess();
        } else {
            return RestResult.buildFail("验证码过期或者错误");
        }
    }
	@EasymisDataSource(DataSourceType.Master)
	public Member findByEmail(String email) {
		// TODO Auto-generated method stub
		return mapper.findByEmail(email);
	}
	@EasymisDataSource(DataSourceType.Master)
	public Member findByUserno(String userno) {
		// TODO Auto-generated method stub
		return null;//mapper.findByUserno(userno);
	}
	@EasymisDataSource(DataSourceType.Master)
	public void updatePassword(Member user) {
		// TODO Auto-generated method stub
		mapper.updatePassword(user);
	}
    protected Member generateUser(String phoneNumber, String password) {
    	Member user = new Member();
        user.setPhoneNumber(phoneNumber);
        user.setPassword(Optional.ofNullable(password).orElse(generatePassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setSex("男");  //默认男
        user.setUserNo(generateUserNo(phoneNumber));
        user.setName(generateUsername());
        return user;
    }

	@Override
	public Mono<User> findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
 
}
