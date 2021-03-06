package com.itopener.demo.es.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itopener.demo.es.enums.RoleStateEnum;
import com.itopener.demo.es.enums.UserStateEnum;
import com.itopener.demo.es.model.Role;
import com.itopener.demo.es.model.User;
import com.itopener.demo.es.service.UserService;
import com.itopener.framework.ResultMap;
import com.itopener.utils.TimestampUtil;

@RestController
@RequestMapping("user")
public class UserController {

	@Resource
	private UserService userService;
	
	@RequestMapping("save")
	public ResultMap save(User user){
		Timestamp now = TimestampUtil.current();
		//long类型存进去后最后4位会被四舍五入掉
		user.setId(new Random().nextLong());
		user.setCreateTime(now);
		user.setName("fuwei.deng");
		user.setState(UserStateEnum.CODE_NORMAL);
		user.setUpdateTime(now);
		
		user.setRoleList(new ArrayList<Role>());
		for(int i=0; i<10; i++){
			Role role = new Role();
			role.setCreateTime(now);
			role.setId(new Random().nextLong());
			role.setName("fuwei.deng." + i);
			role.setState(RoleStateEnum.CODE_NORMAL);
			role.setUpdateTime(now);
			user.getRoleList().add(role);
		}
		
		user = userService.save(user);
		return ResultMap.buildSuccess().put("user", user);
	}
	
	@RequestMapping("{id}")
	public ResultMap get(@PathVariable long id){
		return ResultMap.buildSuccess().put("user", userService.get(id));
	}
	
	@RequestMapping("clear")
	public ResultMap clear(){
		userService.clear();
		return ResultMap.buildSuccess();
	}
}
