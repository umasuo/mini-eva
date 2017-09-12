package cn.eva.mini.domain.service

import cn.eva.mini.application.dto.product.function.FunctionDataType
import cn.eva.mini.application.dto.product.function.StringType
import cn.eva.mini.domain.entity.ProductFunction
import cn.eva.mini.domain.entity.ProductType
import cn.eva.mini.infra.enums.TransferType
import cn.eva.mini.infra.repository.ProductTypeRepository
import spock.lang.Specification

/**
 * Product type service.
 */
class ProductTypeServiceTest extends Specification {

    ProductTypeService productTypeService
    ProductTypeRepository repository

    def id = "ASDASDASDAISDASDOASDK"
    def version = 0
    def name = "testName"
    def description = "test product type"
    def groupName = "testGroup"
    ProductType productType

    ProductFunction productFunction1 = new ProductFunction(id: "productFunction1", name: "switch", description: "description", dataType: new StringType(), transferType: TransferType.UPDOWN)
    ProductFunction productFunction2 = new ProductFunction(id: "productFunction2", name: "switch", description: "description", dataType: new StringType(), transferType: TransferType.UPDOWN)
    List<ProductFunction> functions = new ArrayList<>()

    def setup() {
        repository = Mock(ProductTypeRepository)
        productTypeService = new ProductTypeService(repository: repository)
        functions.add(productFunction1)
        functions.add(productFunction2)
        productType = new ProductType(id: id, version: version, name: name, description: description, groupName: groupName, functions: functions)
    }

    def "Test 1.1 Save product type"() {
        when:
        repository.save(productType) >> productType
        def result = productTypeService.save(productType)
        then:
        result == productType
    }
}
