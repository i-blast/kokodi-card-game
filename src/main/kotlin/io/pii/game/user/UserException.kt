package io.pii.game.user

class UserNotFoundException(login: String) : RuntimeException("User with login '$login' not found")

class InvalidCredentialsException : RuntimeException("Invalid credentials")
