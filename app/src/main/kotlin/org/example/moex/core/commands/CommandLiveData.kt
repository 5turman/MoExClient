package org.example.moex.core.commands

import androidx.annotation.MainThread
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.LinkedList

// TODO test this class
class CommandLiveData : LiveData<Command>() {

    private val queue = LinkedList<Command>()

    override fun observe(owner: LifecycleOwner, observer: Observer<in Command>) {
        check(!hasObservers()) { "Multiple observers are not permitted" }

        super.observe(owner, Observer<Command> { command ->
            if (command != null) {
                observer.onChanged(command)

                while (queue.isNotEmpty()) {
                    observer.onChanged(queue.removeFirst())
                }

                value = null
            }
        })
    }

    @MainThread
    fun add(command: Command) {
        check(ArchTaskExecutor.getInstance().isMainThread) {
            "Cannot invoke add on a background thread"
        }

        if (value == null) {
            value = command
        } else {
            queue.add(command)
        }
    }

}
