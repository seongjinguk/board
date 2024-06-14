package com.btistudy.board.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/* 컨트롤러에 대한 테스트 (슬라이스 테스트)
    슬라이스 테스트: 레이어별로 잘라서 , 특정 부분만 테스트 하는거

    - 대표적인 통합 테스트 어노테이션
        1) @SpringBootTest - 스프링이 관리하는 모든 빈을 등록 시켜서 테스트 함

    - 대표적인 슬라이스 테스트 어노테이션
        1) @WebMvcTest - Contoller 를 테스트 할때 주로 사용
                         @WebMvcTest 선언하면 Web과 관련 Bean만 주입이 되고, MockMvc 를 알아볼 수 있게 됨

                         *MockMvc는 웹 어플리케이션을 서버에 배포하지 않고 테스트용 MVC 환경을 만들어서 요청 및 전송
                         *쉽게 말하면 내가 컨트롤러 테스트를 하고 싶을때 실제 서버에 구현된 어플리케이션을 올리지 않고 테스트용 
                          시뮬레이션을 돌려서 MVC 가 되도록 도와주는 클래스

        2) @DataJpaTest - JPA 레포지토리 테스트 할때 사용
                            @Entity 가 있는 엔티티 클래스들을 스캔해서 테스트를 위한 환경을 설정함
                            @Component 같은 Bean들은 스캔되지 않음
                            
        3) @RestClientTest - 클라이언트 입장에서의 API 연동 테스트
                             테스트 코드 내에서 Mock 서버를 띄울수 있게 됨

            

 */


/* 실패하는 테스트 만들거임 */
@WebMvcTest // 슬라이스 테스트. 컨트롤러 관련된 Bean들만 로드 함.
            // 이 언노테이션은 컨트롤러와 연관된 내용만 최소한으로 로드 하기 때문에 data rest 의 autoConfigration을 읽지는 않는다.
public class Ex04_1_DataRestRepositoryTest {
    /* MockMvc 테스트 방법
        1) MockMvc 생성 (빈 준비)
        2) MockMvc에 요청에 대한 정보를 입력함
        3) 요청에 대한 응답값을 Expect를 이용해서 테스트 함
        4) Expect 가 모두 통과하면 통과
           하나라도 실패하면 실패

        Hal 에서 http://localhost:8080/api/article 를 돌려보면 동작하는데 왜 여기서는 안되냐면
        @WebMveTest 는 슬라이스 테스트 이기 때문에 controller 이외의 빈은 아예 로그하지 않기 때문임
        그래서 이런 경우에는 @WebMveTest 대신 통합테스트를 사용할거임.
     */

    private final MockMvc mockMvc;

    public Ex04_1_DataRestRepositoryTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
    
    @DisplayName("[apo] 게시글 리스트 조회")
    @Test
    void test1() throws Exception {
        // given

        // when & then

        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk()) // 현재 상태가 200인가(존재 하냐? 라고 물어보는거)
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));


        /* get() 작성하는법
            1) perform() 안에 get 이라고 친다.
            2) 추천에 getClass()만 뜰건데 그상태에서 Ctrl + space 누른다
            3) 추천 리스트가 뜨는데 그중에 MockMvcRequestBuilders 있을거임. 그거 alt + 엔터 선택
                윈도우 : alt + enter
                맥: 옵션 + 엔터
                이렇게 하면 get() 부분 가져와진다 (static import 방식)
            4) 3까지 하면 맨 위에 import 부분에 "import static ~~" 생긴다

            5) status() 같은 방법으로 MockMvcResultMatchers.status; 선택한다
               (alt + enter)

            6) content() 는 그냥 일반적으로 하면 됨


        
        
         */

    }


    
}












