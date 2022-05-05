package com.breaktime.classmates.util

class ListIteratorImpl<out E>(private val list: List<E>) : ListIterator<E> {
    private var currentPos = -1

    override fun hasNext() = currentPos < list.size - 1

    override fun hasPrevious() = currentPos > 0

    override fun next() = list[++currentPos]

    override fun nextIndex() = currentPos + 1

    override fun previous() = list[--currentPos]

    override fun previousIndex() = currentPos - 1
}

fun <E> List<E>.getListIterator(): ListIterator<E> = ListIteratorImpl(this)
