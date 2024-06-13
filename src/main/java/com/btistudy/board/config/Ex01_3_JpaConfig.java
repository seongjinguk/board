package com.btistudy.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;


/* @Configuration 이라고 하면 JPA가 '이건 설정파일' 이라고 인식해서 Configration Bean 으로 등록함 */
@Configuration
@EnableJpaAuditing /* JPA 에서 auditing 을 가능하게 하는 어노테이션
                        jpa auditing: 감시, 감사
                                      Spring Data Jpa 에서 자동으로 값 넣어주는 기능
 */

public class Ex01_3_JpaConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        // 람다식(자바스크립트에서 화살표 함수 같은거)
        return ()-> Optional.of("bitstudy");
        // 이렇게 하면 앞으로 JPA Auditing 할때마나 사람 이름은 bitstudy 로 넣게 됨
        // TODO: 나중에 스프링 시큐리티로 인증 기능 붙일때 수정할거임



    }

}
