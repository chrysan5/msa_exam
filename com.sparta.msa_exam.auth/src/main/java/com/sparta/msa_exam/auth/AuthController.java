package com.sparta.msa_exam.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 사용자 ID를 받아 JWT 액세스 토큰을 생성하여 응답합니다.
     *
     * @param user_id 사용자 ID
     * @return JWT 액세스 토큰을 포함한 AuthResponse 객체를 반환합니다.
     */
    //원래 id받아오는 것이므로 postMapping으로 해야한다 (get방식은 노출이 되므로) 하지만 연습이므로 getMapping으로 함
    @GetMapping("/auth/signIn")
    public ResponseEntity<?> createAuthenticationToken(@RequestParam String user_id){
        return ResponseEntity.ok(new AuthResponse(authService.createAccessToken(user_id)));
    }
    //server app과 auth app을 띄우고
    // localhost:19095/auth/signIn?user_id=aaa 하면 accessToken 값을 확인할 수 있다.

    /**
     * JWT 액세스 토큰을 포함하는 응답 객체입니다.
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class AuthResponse {
        private String access_token;
    }
}
//기본 유저는 gateway filter이서 처리하고, 특정 권한을 가진(상위 권한(관리자))은 auth app에서 처리한다
//또는 gateway에서는 로그인 여부만 확인하고 각 product, order 에서 권한이 필요시 auth를 각각 호출할 수도 있다.

/*localhost:19095/auth/signIn?user_id=aaa - server app, auth app 만 있을 경우 토큰 확인 가능
  localhost:19091/auth/signIn?user_id=aaa - 게이트웨이 켤 경우 여기서 토큰 확인 가능, 위의 주소에서도 가능
  여기서 받은 토큰을 포스트맨이나/talend api에서 헤더에 넣고
  localhost:19091/product 혹은 localhost:19093(4)/product 하면 접근 가능하다.*/