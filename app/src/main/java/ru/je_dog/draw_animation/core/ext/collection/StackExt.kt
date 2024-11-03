package ru.je_dog.draw_animation.core.ext.collection

import java.util.Stack

fun<T> Stack<T>.popOrNull(): T? {
    if (isEmpty()) return null
    return pop()
}
