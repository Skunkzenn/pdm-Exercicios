package com.victor.listacompras.models

data class User(
    val uid: String = "", // Identificador único do usuário (geralmente Firebase UID)
    val name: String? = null, // Nome do usuário
    val email: String? = null // Email do usuário, caso queira exibir ou usar
) {
    constructor() : this("", null, null) // Construtor vazio necessário para Firestore
}
