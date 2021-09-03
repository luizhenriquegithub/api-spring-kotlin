package br.net.uno.contatos.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Size

@Entity(name = "accounts")
data class Account(
        @Id @GeneratedValue
        var id: Long? = null,

        @field:Size(max = 50)
        val name: String,

        @field:Size(max = 14)
        val document: String,

        @field:Size(max = 14)
        val phone: String
)

