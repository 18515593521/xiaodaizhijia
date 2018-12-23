package com.dd.supermarket.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
* @author 	作者 ：	  <br/>
*			E-mail:	  <br/>
* @version 	创建时间：	2018年6月20日 下午8:19:32 <br/>
* 类说明：
*/
/*重要！如果你的项目引入junit测试，此处需要使用@WebAppConfiguration，如果没有使用junit使用@Configuration(很多的博客都没有注明这个问题，为此我花了非常多的时间解决问题)*/
@WebAppConfiguration
@EnableSwagger2
@EnableWebMvc
//@ComponentScan(basePackages = "com.dd.supermarket.controller")//扫描control所在的package请修改为你control所在package
public class SwaggerConfig extends WebMvcConfigurerAdapter {
	private final String APPPATH = "com.dd.supermarket.controller.app";
	


	/**
	 * APP所有接口合并文档
	 * @return
	 */
    @Bean
    public Docket app_api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
        		.apis(RequestHandlerSelectors.basePackage(this.APPPATH))
                .paths(PathSelectors.any())
                .build()
                .groupName("app接口合并文档")
                .apiInfo(this.apiInfo("APP所有接口文档","该文档中可以查询所有的接口调用参数和结果","1.0"));
    }

	/**
	 * APP所需基础接口文档
	 * @return
	 */
    @Bean
    public Docket app_basics() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
        		.apis(RequestHandlerSelectors.basePackage(this.APPPATH+".basics"))
                .paths(PathSelectors.any())
                .build()
                .groupName("APP基础接口文档")
                .apiInfo(this.apiInfo("APP所需基础接口文档","该文档中可以查询APP所需的密钥、开关","1.0"));
    }
	/**
	 * APP登录、注册、修改密码接口文档
	 * @return
	 */
    @Bean
    public Docket app_Login() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
        		.apis(RequestHandlerSelectors.basePackage(this.APPPATH+".login"))
                .paths(PathSelectors.any())
                .build()
                .groupName("APP登录、注册、修改密码接口文档")
                .apiInfo(this.apiInfo("APP登录注册修改密码接口文档","该文档中可以登录、注册、修改密码","1.0"));
    }
	/**
	 * APP发送验证码短信接口文档
	 * @return
	 */
    @Bean
    public Docket app_sendMessage() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
        		.apis(RequestHandlerSelectors.basePackage(this.APPPATH+".newMessage"))
                .paths(PathSelectors.any())
                .build()
                .groupName("APP短信验证码接口文档")
                .apiInfo(this.apiInfo("APP短信验证码接口文档","该文档中可以登录、注册、修改密码","1.0"));
    }
    
	/**
	 * APP首页接口文档
	 * @return
	 */
    @Bean
    public Docket app_home() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
        		.apis(RequestHandlerSelectors.basePackage(this.APPPATH+".home"))
                .paths(PathSelectors.any())
                .build()
                .groupName("APP首页接口文档")
                .apiInfo(this.apiInfo("APP首页接口文档","该文档中可以获取首页信息","1.0"));
    }
    
	/**
	 * APP首页接口文档
	 * @return
	 */
    @Bean
    public Docket app_user() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select()
        		.apis(RequestHandlerSelectors.basePackage(this.APPPATH+".user"))
                .paths(PathSelectors.any())
                .build()
                .groupName("APP我的接口文档")
                .apiInfo(this.apiInfo("APP我的接口文档","该文档中可以获取浏览记录、关于我们、问题反馈与邀请好友","1.0"));
    }
    
    
    
    
    
    
    private ApiInfo apiInfo(String name,String description,String version) {
        ApiInfo apiInfo = new ApiInfoBuilder()
        		.title(name)
        		.description(description)
        		.version(version)
        		.build();
        return apiInfo;
    }
	
}