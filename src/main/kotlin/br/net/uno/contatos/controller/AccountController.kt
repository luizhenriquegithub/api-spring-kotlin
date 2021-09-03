package br.net.uno.contatos.controller

import br.net.uno.contatos.model.Account
import br.net.uno.contatos.repository.account.AccountRepository
import br.net.uno.contatos.service.account.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/accounts")
class AccountController(private val service: AccountService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody account: Account): Account = service.create(account)

    @GetMapping
    fun getAll(): List<Account> = service.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Long): ResponseEntity<Account> =
            service.getById(id).map {
                ResponseEntity.ok(it)
            }.orElse(ResponseEntity.notFound().build())

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody account: Account): ResponseEntity<Account> =
            service.update(id, account).map {
                ResponseEntity.ok(it)
            }.orElse(ResponseEntity.notFound().build())


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity<Void>(HttpStatus.OK)
    }

}