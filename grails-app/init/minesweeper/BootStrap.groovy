package minesweeper

import com.dmc.minesweeper.security.Authority
import com.dmc.minesweeper.security.User
import com.dmc.minesweeper.security.UserAuthority

class BootStrap {

    def init = { servletContext ->

        UserAuthority.withNewTransaction {
            def role1 = new Authority(authority: "ROLE_PLAYER").save flush: true
            def user1 = new User(username: "diego", password: "diego").save flush: true
            UserAuthority.create(user1, role1)
        }

    }
    def destroy = {
    }
}
