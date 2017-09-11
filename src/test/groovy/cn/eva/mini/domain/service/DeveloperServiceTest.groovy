package cn.eva.mini.domain.service

import cn.eva.mini.domain.entity.Developer
import cn.eva.mini.infra.repository.DeveloperRepository
import cn.eva.mini.infra.util.PasswordUtil
import spock.lang.Specification

/**
 * Developer service test.
 */
class DeveloperServiceTest extends Specification {

    DeveloperService developerService

    DeveloperRepository developerRepository

    def id = "QWERTYUIOPASDFGHJ"

    def email = "test@umasuo.com"

    def rawPassword = "password"

    def hashedPassword = PasswordUtil.hashPassword(rawPassword)

    def setup() {
        developerRepository = Mock(DeveloperRepository)
        developerService = new DeveloperService(repository: developerRepository)
    }

    def "Test 1.1: create developer with sample"() {
        Developer developer = new Developer(id: id, email: email, password: hashedPassword)
        when:
        developerRepository.save(_) >> developer
        def result = developerService.save(developer)
        then:
        noExceptionThrown()
        result.getId() == developer.getId()
    }
}
