package cn.eva.mini.domain.service

import cn.eva.mini.domain.entity.User
import cn.eva.mini.infra.exception.AlreadyExistException
import cn.eva.mini.infra.exception.NotExistException
import cn.eva.mini.infra.repository.UserRepository
import cn.eva.mini.infra.util.PasswordUtil
import org.springframework.data.domain.Example
import spock.lang.Specification

/**
 * User service test case.
 */
class UserServiceTest extends Specification {

    UserService userService

    UserRepository userRepository

    def id = "WEFBWEKFBOSIDHAOSNDAOI"
    def rawPassword = "password"
    def hashedPassword = PasswordUtil.hashPassword(rawPassword)
    def version = 0
    def email = "test@evacloud.cn"
    def phone = "+86 1234567890"
    def externalId = "ALDOIAHSCLNKASCIUAOIFNAS"
    def developerId = "LSDKFBASBDABIFBDASBIDU"
    User user


    def setup() {
        userRepository = Mock(UserRepository)
        user = new User(id: id, password: hashedPassword, version: version, email: email, phone: phone, externalId: externalId, developerId: developerId)
        userService = new UserService(userRepository: userRepository)
    }

    def "Test 1.1: Create new user with sample"() {

        when:
        userRepository.save(user) >> user
        User newUser = userService.create(user)
        then:
        newUser == user
    }

    def "Test 1.2: Create new user with sample that already exist"() {

        Example<User> example = Example.of(user);
        when:
        userRepository.save(user) >> user
        userRepository.findOne(example) >> user
        userService.create(user)
        then:
        thrown(AlreadyExistException)
    }

    def "Test 2.1: Save user"() {

        when:
        userRepository.save(user) >> user
        User savedUser = userService.save(user)
        then:
        savedUser == user
    }

    def "Test 3.1: Get user by id"() {

        when:
        userRepository.findOne(id) >> user
        User savedUser = userService.getById(id)
        then:
        savedUser == user
    }

    def "Test 3.2: Get user by id that not exist"() {

        when:
        userRepository.findOne(id) >> null
        userService.getById(id)
        then:
        thrown(NotExistException)
    }

    def "Test 4.1: Get user by phone"() {

        when:
        userRepository.findOneByPhone(phone) >> user
        User savedUser = userService.getByPhone(phone)
        then:
        savedUser == user
    }

    def "Test 4.2: Get user by email that not exist"() {

        when:
        userRepository.findOneByPhone(phone) >> null
        userService.getByPhone(phone)
        then:
        thrown(NotExistException)
    }
}
