package br.net.uno.contatos.repository.account

import br.net.uno.contatos.model.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<Account, Long> {

}