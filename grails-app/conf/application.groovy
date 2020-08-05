grails {
    plugin {
        springsecurity {
            rest {
                token {
                    storage {
                        jwt {
                            secret = 'pleaseChangeThisSecretForANewOne'
                        }
                    }
                }
            }
            securityConfigType = "InterceptUrlMap"
            filterChain {
                chainMap = [
                        [pattern: '/api/**', filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],
                        [pattern: '/**', filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
                ]
            }
            userLookup {
                userDomainClassName = 'com.dmc.minesweeper.security.User'
                authorityJoinClassName = 'com.dmc.minesweeper.security.UserAuthority'
            }
            authority {
                className = 'com.dmc.minesweeper.security.Authority'
            }
            interceptUrlMap = [
                    [pattern: '/', access: ['permitAll']],
                    [pattern: '/error', access: ['permitAll']],
                    [pattern: '/index', access: ['permitAll']],
                    [pattern: '/index.gsp', access: ['permitAll']],
                    [pattern: '/shutdown', access: ['permitAll']],
                    [pattern: '/assets/**', access: ['permitAll']],
                    [pattern: '/**/js/**', access: ['permitAll']],
                    [pattern: '/**/css/**', access: ['permitAll']],
                    [pattern: '/**/images/**', access: ['permitAll']],
                    [pattern: '/**/favicon.ico', access: ['permitAll']],
                    [pattern: '/api/login', access: ['permitAll']],
                    [pattern: '/api/logout', access: ['isFullyAuthenticated()']],
                    [pattern: '/api/game', access: ['isFullyAuthenticated()']],
                    [pattern: '/**', access: ['isFullyAuthenticated()']]
            ]
        }
    }
}

grails.plugin.springsecurity.rest.logout.endpointUrl = '/api/logout'
