package com.gec.ecommerce.mapper;

import com.gec.ecommerce.bases.BaseMapper;
import com.gec.ecommerce.domain.User;
import com.gec.ecommerce.dto.UserShallowDto;
import com.gec.ecommerce.dto.request.UserRequest;
import com.gec.ecommerce.dto.response.UserResponse;
import com.gec.ecommerce.filter.UserFilter;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class UserMapper extends BaseMapper<User, UserFilter, UserShallowDto, UserRequest, UserResponse>
{}
