package br.net.uno.contatos.repository.account

import br.net.uno.contatos.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AccountRepository: JpaRepository<Account, Long> {

}