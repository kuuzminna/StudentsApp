package ru.tinkoff.favouritepersons.pages

import com.kaspersky.kaspresso.screens.KScreen

abstract class BasePage : KScreen<BasePage>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null
}