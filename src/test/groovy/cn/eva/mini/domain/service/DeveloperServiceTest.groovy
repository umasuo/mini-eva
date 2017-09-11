package cn.eva.mini.domain.service

import cn.eva.mini.domain.entity.Developer
import cn.eva.mini.infra.enums.AccountStatus
import cn.eva.mini.infra.exception.AlreadyExistException
import cn.eva.mini.infra.exception.NotExistException
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

    def version = 0

    def email = "test@evacloud.cn"

    def rawPassword = "password"

    def hashedPassword = PasswordUtil.hashPassword(rawPassword)

    Developer developer = new Developer(id: id, version: version, email: email, password: hashedPassword, status: AccountStatus.UNVERIFIED)

    def setup() {
        developerRepository = Mock(DeveloperRepository)
        developerService = new DeveloperService(repository: developerRepository)
    }

    def "Test 1.1: Save developer"() {
        when:
        developerRepository.save(_) >> developer
        def result = developerService.save(developer)
        then:
        noExceptionThrown()
        result.getId() == developer.getId()
    }

    def "Test 2.1: Create developer with email and password"() {
        when:
        developerRepository.findOneByEmail(email) >> null
        developerRepository.save(_) >> developer
        def result = developerService.create(email, rawPassword)
        then:
        noExceptionThrown()
        result.getId() == developer.getId()
    }

    def "Test 2.2: Create developer with email which already exist"() {
        when:
        developerRepository.findOneByEmail(email) >> developer
        developerRepository.save(_) >> developer
        developerService.create(email, rawPassword)
        then:
        thrown(AlreadyExistException)
    }

    def "Test 3.1: Get developer by id"() {
        when:
        developerRepository.findOne(id) >> developer
        Developer result = developerService.get(id)
        then:
        result == developer
    }

    def "Test 3.2: Get developer by id that not exist"() {
        when:
        developerRepository.findOne(id) >> null
        Developer result = developerService.get(id)
        then:
        result == null
    }

    def "Test 4.1: Get developer by email"() {
        when:
        developerRepository.findOneByEmail(email) >> developer
        Developer result = developerService.getWithEmail(email)
        then:
        result == developer
    }

    def "Test 4.2: Get developer by email that not exist"() {
        when:
        developerRepository.findOneByEmail(email) >> null
        developerService.getWithEmail(email)
        then:
        thrown(NotExistException)
    }

    def "Test 5.1: Count developer number"() {
        when:
        developerRepository.count() >> 10
        long count = developerService.countDevelopers()
        then:
        count == 10
    }

    def "Test 6.1: Get all developers"() {
        List<Developer> result = new ArrayList<>()
        result.add(developer)
        when:
        developerRepository.findAll() >> result
        List<Developer> values = developerService.getAllDevelopers()
        then:
        values.size() == 1
        values == result
    }


}
