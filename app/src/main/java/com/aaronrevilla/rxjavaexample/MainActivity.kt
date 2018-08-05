package com.aaronrevilla.rxjavaexample

import android.os.Bundle
import android.support.annotation.IntegerRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            displayText.setText("")
            var buffer = StringBuffer()
            Observable.just(getArrayListOfRandomNum())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(object:Observer<ArrayList<Int>>{
                        override fun onComplete() {
                            displayText.setText(buffer.toString())
                        }

                        override fun onSubscribe(d: Disposable) {
                            Log.d("RxJava", "OnSubscribe")
                        }

                        override fun onNext(numbers: ArrayList<Int>) {
                            for (number in numbers)
                                buffer.append(number.toString() + ",")
                        }

                        override fun onError(e: Throwable) {
                            Log.d("RxJava", "Error")
                        }

                    })
        }




    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getArrayListOfRandomNum(): ArrayList<Int>{
        var randomNumbers = arrayListOf<Int>()
        for (i in 1..100){
            randomNumbers.add(getRandomNumbers())
        }
        return randomNumbers
    }

    fun getRandomNumbers(): Int{
        //var rand = Random()
        //return rand.nextInt();
        return Random().nextInt()//same result different approach
    }

}
