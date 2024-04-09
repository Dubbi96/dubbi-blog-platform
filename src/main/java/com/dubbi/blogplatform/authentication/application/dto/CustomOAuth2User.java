package com.dubbi.blogplatform.authentication.application.dto.dto;

import com.dubbi.blogplatform.common.enumeratedclasses.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;


/** super()로 부모 객체인 DefaultOAuth2User를 생성하고,
 *  email과 role 파라미터를 추가로 받아서, 주입하여 CustomOAuth2User를 생성합니다.*/
@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private final String email;
    private final Role role;

    /**
     * Constructs a {@code DefaultOAuth2User} using the provided parameters.
     *
     * @param authorities      the authorities granted to the user
     * @param attributes       the attributes about the user
     * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
     *                         {@link #getAttributes()}
     */
    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            String email, Role role) {
                super(authorities, attributes, nameAttributeKey);
                this.email = email;
                this.role = role;
    }

    /**equals, hasCode function @Override한 이유는
     * 1. Object.equals()를 상속받아 그대로 사용하게 되면 올바른 인스턴스 타입인지 확인이 불가함 (상속받은 인스턴스에 대한 체크는 하지 못함)
     * 2. @Override한 equals() 함수는 filter 형식으로 체크하며, CustomOAuth2User A이라면 A.equals일때 아래의 순서로 확인 함 (A.equals(SOME_OBJ)일 경우)
     *
     * A.equals(SOME_OBJ)
     * 1. A와 SOME_OBJ이 정확히 일치하는지 확인 (A = 다른 변수로 대입한 A)
     * 2. 1번의 경우가 아닐 경우, SOME_OBJ가 CustomOAuth2User의 인스턴스인지 확인
     * 3. 2번이 참일 경우 Object.equals()에서 정의된 함수를 사용하여 확인 (아래는 상속받은 class의 equals)
     * public boolean equals(Object obj) {
     * 		if (this == obj) {
     * 			return true;
     *                }
     * 		if (obj == null || this.getClass() != obj.getClass()) {
     * 			return false;
     *        }
     * 		DefaultOAuth2User that = (DefaultOAuth2User) obj;
     * 		if (!this.getName().equals(that.getName())) {
     * 			return false;
     *        }
     * 		if (!this.getAuthorities().equals(that.getAuthorities())) {
     * 			return false;
     *        }
     * 		return this.getAttributes().equals(that.getAttributes());* 	}
     * 4. 모든 결과를 확인해도 결과가 나오지 않는다면, 캐스팅한 that에 있는 email, role값을 비교*/
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        //other이 CustomOAuth2User의 instance라면 that에 CustomOAuth2User타입으로 자동 캐스팅
        if (!(other instanceof CustomOAuth2User that)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        return Objects.equals(email, that.email) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, role);
    }
}
