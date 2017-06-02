package org.example.moex

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Executor

/**
 * Created by 5turman on 07.05.2017.
 */
class TestSchedulerRule : TestRule {

    private val immediate = object : Scheduler() {
        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
        }
    }

    val testScheduler = TestScheduler()

    override fun apply(base: Statement, description: Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { _ -> testScheduler }
                RxJavaPlugins.setComputationSchedulerHandler { _ -> testScheduler }
                RxJavaPlugins.setNewThreadSchedulerHandler { _ -> testScheduler }
                RxAndroidPlugins.setMainThreadSchedulerHandler { _ -> immediate }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                }
            }
        }
    }

}
