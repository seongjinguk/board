package com.btistudy.board.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("API 테스트")
@SpringBootTest /* 통합으로 구현하기 위해서 여기 변경함*/
@AutoConfigureMockMvc /* @SpringBootTest 랑 항상 같이 쓰여야 테스트 통과함 */
@Transactional /* 테스트를 돌리면 콘솔창에 Hibernate 부분에 select 쿼리가 같이 나온다. 
                이 말은 실제 repository 까지 실행시켰다는 뜻인데 한마디로 DB를 건드렸을수도 있다는 뜻이다.
                 그래서 이건 테스트니까 트랜잭션으로 묶어서 롤백까지 시켜주기 위해서 사용 */
public class Ex04_2_DataRestRepositoryTest {

    // 빈 준비
    // MockMvc: 실제 객체와 비슷하지만 테스트에 필요한 기능만 가지고 있는 가짜 객체를 만들어서 실제 서버를 돌리지 않고도 스프링 MVC 동작을 할 수 있는 클래스
//    @Autowired MockMvc mockMvc;
    private final MockMvc mockMvc;

    // MockMvc를 생성자 방식으로 주입 받기
    public Ex04_2_DataRestRepositoryTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @DisplayName("[api] 게시글 리스트 조회")
    @Test
    void test1() throws Exception {
        // given

        // when & then

        mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk()) // 현재 상태가 200인가(존재 하냐? 라고 물어보는거)
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
 

    }
    @DisplayName("[api] 게시글 단건 조회")
    @Test
    void articleOne() throws Exception {
        // given

        // when & then

        mockMvc.perform(get("/api/articles/1")) // 테스트 데이터가 있다고 가정하고 있는거임. (1번글 하나 가져와라 라는 뜻)
                .andExpect(status().isOk()) // 현재 상태가 200인가(존재 하냐? 라고 물어보는거)
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));


    }

    @DisplayName("[api] 댓글 리스트 조회") // ArticleComment 에 있는 모든 댓글이 아니라 특정 Article의 댓글들 전체 조회인거임
    @Test
    void articleCommentAll() throws Exception {
        // given

        // when & then

        mockMvc.perform(get("/api/articles/1/articleComments"))
                .andExpect(status().isOk()) // 현재 상태가 200인가(존재 하냐? 라고 물어보는거)
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));


    }

    @DisplayName("[api] 댓글 단일 조회") // ArticleComment 에 있는 모든 댓글이 아니라 특정 Article의 댓글들 전체 조회인거임
    @Test
    void articleCommentOne() throws Exception {
        // given

        // when & then

        mockMvc.perform(get("/api/articles/1/articleComment/1"))
                .andExpect(status().isOk()) // 현재 상태가 200인가(존재 하냐? 라고 물어보는거)
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));


    }
}












