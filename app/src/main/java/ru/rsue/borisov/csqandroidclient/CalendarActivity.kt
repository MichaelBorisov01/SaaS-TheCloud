package ru.rsue.borisov.csqandroidclient

import android.content.ClipData
import android.graphics.Color
import android.graphics.RectF
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.View.OnLongClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import me.jlurena.revolvingweekview.DayTime
import me.jlurena.revolvingweekview.WeekView
import me.jlurena.revolvingweekview.WeekView.*
import me.jlurena.revolvingweekview.WeekViewEvent
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.TextStyle
import java.util.*


/**
 * This is a base activity which contains week view and all the codes necessary to initialize the
 * week view.
 */
open class CalendarActivity : AppCompatActivity(), EventClickListener, WeekViewLoader,
    EventLongPressListener, EmptyViewLongPressListener, EmptyViewClickListener,
    AddEventClickListener, DropListener {
    private lateinit var mWeekView: WeekView
    private var mWeekViewType = TYPE_THREE_DAY_VIEW
    private fun getEventTitle(time: DayTime): String {
        return String.format(
            Locale.getDefault(), "Event of %s %02d:%02d", time.day.getDisplayName(
                TextStyle.FULL, Locale.getDefault()
            ), time.hour, time.minute
        )
    }

    override fun onAddEventClicked(startTime: DayTime, endTime: DayTime) {
        Toast.makeText(this, "Add event clicked.", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar)
        val draggableView = findViewById<TextView>(R.id.draggable_view)
        draggableView.setOnLongClickListener(DragTapListener())


        // Get a reference for the week view in the layout.
        mWeekView = findViewById(R.id.weekView)

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this)

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.weekViewLoader = this

        // Set long press listener for events.
        mWeekView.eventLongPressListener = this

        // Set long press listener for empty view
        mWeekView.emptyViewLongPressListener = this

        // Set EmptyView Click Listener
        mWeekView.emptyViewClickListener = this

        // Set AddEvent Click Listener
        mWeekView.addEventClickListener = this

        // Set Drag and Drop Listener
        mWeekView.setDropListener(this)

        //mWeekView.setAutoLimitTime(true)
        //mWeekView.setLimitTime(8, 24)

        mWeekView.minTime = 8
        mWeekView.maxTime = 24

        mWeekView.overlappingEventGap = 3

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        //setupDateTimeInterpreter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.calendar_menu, menu)
        return true
    }

    override fun onDrop(view: View, day: DayTime) {
        Toast.makeText(this, "View dropped to $day", Toast.LENGTH_SHORT).show()
    }

    // Функция события при нажатии на пустую ячейку
    override fun onEmptyViewClicked(day: DayTime) {

        Toast.makeText(this, "Empty view" + " clicked: " + getEventTitle(day), Toast.LENGTH_SHORT)
            .show()
    }

    // Функция события при долгом нажатии на пустую ячейку
    override fun onEmptyViewLongPress(time: DayTime) {
        Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT)
            .show()
    }

    // Функция события при нажатии на занятую ячейку
    override fun onEventClick(event: WeekViewEvent, eventRect: RectF) {
        Toast.makeText(this, "Clicked $event", Toast.LENGTH_SHORT).show()
    }

    // Функция события при долгом нажатии на занятую ячейку
    override fun onEventLongPress(event: WeekViewEvent, eventRect: RectF) {
        Toast.makeText(this, "Long pressed event: " + event.name, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //setupDateTimeInterpreter()
        when (item.itemId) {
            R.id.action_today -> {
                mWeekView.goToToday()
                return true
            }
            R.id.action_day_view -> {
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.isChecked = !item.isChecked
                    mWeekViewType = TYPE_DAY_VIEW
                    mWeekView.numberOfVisibleDays = 1

                    // Lets change some dimensions to best fit the view.
                    mWeekView.columnGap =
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8f,
                            resources.displayMetrics
                        ).toInt()
                    mWeekView.textSize = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 12f,
                        resources.displayMetrics
                    ).toInt()
                    mWeekView.eventTextSize =
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 12f,
                            resources.displayMetrics
                        ).toInt()
                }
                return true
            }
            R.id.action_three_day_view -> {
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.isChecked = !item.isChecked
                    mWeekViewType = TYPE_THREE_DAY_VIEW
                    mWeekView.numberOfVisibleDays = 3

                    // Lets change some dimensions to best fit the view.
                    mWeekView.columnGap =
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 8f,
                            resources.displayMetrics
                        ).toInt()
                    mWeekView.textSize = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 12f,
                        resources.displayMetrics
                    ).toInt()
                    mWeekView.eventTextSize =
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 12f,
                            resources.displayMetrics
                        ).toInt()
                }
                return true
            }
            R.id.action_week_view -> {
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.isChecked = !item.isChecked
                    mWeekViewType = TYPE_WEEK_VIEW
                    mWeekView.numberOfVisibleDays = 7

                    // Lets change some dimensions to best fit the view.
                    mWeekView.columnGap =
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 2f,
                            resources.displayMetrics
                        ).toInt()
                    mWeekView.textSize = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 10f,
                        resources.displayMetrics
                    ).toInt()
                    mWeekView.eventTextSize =
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 10f,
                            resources.displayMetrics
                        ).toInt()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Функция для заполнения календаря событиями из сервера
    override fun onWeekViewLoad(): List<WeekViewEvent> {
        val events: MutableList<WeekViewEvent> = ArrayList()
        for (i in 0..3) {
            val startTime =
                DayTime(LocalDateTime.now().plusHours((i * (random.nextInt(3) + 1)).toLong()))
            val endTime = DayTime(startTime)
            endTime.addMinutes(random.nextInt(30) + 30)
            val event = WeekViewEvent("ID$i", "Event $i", startTime, endTime)
            event.color = randomColor()
            events.add(event)
        }
        return events
    }

    // Функция настройки времени, отображаемого слева от календаря
    private fun setupDateTimeInterpreter() {
        mWeekView.dayTimeInterpreter = object : DayTimeInterpreter {
            override fun interpretDay(date: Int): String {
                return DayOfWeek.of(date).getDisplayName(TextStyle.FULL, Locale.getDefault())
            }

            override fun interpretTime(hour: Int, minutes: Int): String {
                val strMinutes = String.format(Locale.getDefault(), "%02d", minutes)
                return if (hour == 8) {
                    (if (hour > 15) "8:$strMinutes" else (8 + hour).toString() + ":" + strMinutes)
                } else {
                    (if (hour > 15) "8:$strMinutes" else (8 + hour).toString() + ":" + strMinutes)
                }
            }
        }
    }

    private inner class DragTapListener : OnLongClickListener {
        override fun onLongClick(v: View): Boolean {
            val data = ClipData.newPlainText("", "")
            val shadowBuilder = DragShadowBuilder(v)
            v.startDragAndDrop(data, shadowBuilder, v, 0)
            return true
        }
    }

    companion object {
        private const val TYPE_DAY_VIEW = 1
        const val TYPE_THREE_DAY_VIEW = 2
        private const val TYPE_WEEK_VIEW = 3
        private val random = Random()

        @ColorInt
        private fun randomColor(): Int {
            return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
        }
    }
}




