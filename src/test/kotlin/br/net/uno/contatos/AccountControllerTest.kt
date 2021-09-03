package br.net.uno.contatos

import br.net.uno.contatos.model.Account
import br.net.uno.contatos.repository.account.AccountRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var repository: AccountRepository

    @Test
    fun `test find all`(){
        repository.save(Account(name = "Teste", document = "123456789", phone = "99200642"))

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$").isArray)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isNumber)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].name").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].document").isString)
                .andExpect(MockMvcResultMatchers.jsonPath("\$[0].phone").isString)
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test find by id`(){
        val account = repository.save(Account(name = "Teste", document = "123456789", phone = "99200642"))
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/accounts/${account.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.id").value(account.id))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test create account`(){
        val account = Account(name = "Teste", document = "123456789", phone = "99200642")
        val json = ObjectMapper().writeValueAsString(account)

        repository.deleteAll()

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
                .andDo(MockMvcResultHandlers.print())

        Assertions.assertFalse(repository.findAll().isEmpty())
    }


    @Test
    fun `test update account`(){
        val account = repository
                .save(Account(name = "Teste", document = "123456789", phone = "99200642"))
                .copy(name = "Update")
        val json = ObjectMapper().writeValueAsString(account)

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/accounts/${account.id}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("\$.name").value(account.name))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.document").value(account.document))
                .andExpect(MockMvcResultMatchers.jsonPath("\$.phone").value(account.phone))
                .andDo(MockMvcResultHandlers.print())

        val findByid = repository.findById(account.id!!)
        Assertions.assertTrue(findByid.isPresent)
        Assertions.assertEquals(account.name, findByid.get().name)
    }


    @Test
    fun `test delete account`(){
        val account = repository.save(Account(name = "Teste", document = "123456789", phone = "99200642"))
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/accounts/${account.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())

        val findByid = repository.findById(account.id!!)
        Assertions.assertFalse(findByid.isPresent)
    }


}