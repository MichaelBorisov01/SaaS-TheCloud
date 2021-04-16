package ru.rsue.borisov.csqandroidclient.ui.user_calendar

import android.content.ClipData
import android.graphics.Color
import android.graphics.RectF
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import me.jlurena.revolvingweekview.DayTime
import me.jlurena.revolvingweekview.WeekView
import me.jlurena.revolvingweekview.WeekViewEvent
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.TextStyle
import ru.rsue.borisov.csqandroidclient.CalendarActivity
import ru.rsue.borisov.csqandroidclient.R
import java.util.*
import me.jlurena.revolvingweekview.WeekView.*


class UserCalendarFragment : Fragment(), EventClickListener, WeekViewLoader,
    EventLongPressListener, EmptyViewLongPressListener, EmptyViewClickListener,
    AddEventClickListener, DropListener {

    private lateinit var viewModel: UserCalendarViewModel
    private lateinit var mWeekView: WeekView
    private var mWeekViewType = CalendarActivity.TYPE_THREE_DAY_VIEW
    private fun getEventTitle(time: DayTime): String {
        return String.format(
            Locale.getDefault(), "Event of %s %02d:%02d", time.day.getDisplayName(
                TextStyle.SHORT, Locale.getDefault()
            ), time.hour, time.minute
        )
    }

    override fun onAddEventClicked(startTime: DayTime, endTime: DayTime) {
        Toast.makeText(activity, "Add event clicked.", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(UserCalendarViewModel::class.java)
        val root = inflater.inflate(R.layout.user_calendar_fragment, container, false)

        val draggableView = root.findViewById<TextView>(R.id.draggable_view_user)
        draggableView.setOnLongClickListener(DragTapListener())


        // Get a reference for the week view in the layout.
        mWeekView = root.findViewById(R.id.week_view_user)

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
        //mWeekView.setLimitTime(4, 16)

        mWeekView.minTime = 8
        mWeekView.maxTime = 24

        setHasOptionsMenu(true)
        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        //setupDateTimeInterpreter()
        return inflater.inflate(R.layout.user_calendar_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserCalendarViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.calendar_menu, menu)

    }

    override fun onDrop(view: View, day: DayTime) {
        Toast.makeText(activity, "View dropped to $day", Toast.LENGTH_SHORT).show()
    }

    // Функция события при нажатии на пустую ячейку
    override fun onEmptyViewClicked(day: DayTime) {
        Toast.makeText(
            activity,
            "Empty view" + " clicked: " + getEventTitle(day),
            Toast.LENGTH_SHORT
        )
            .show()
    }

    // Функция события при долгом нажатии на пустую ячейку
    override fun onEmptyViewLongPress(time: DayTime) {
        Toast.makeText(
            activity,
            "Empty view long pressed: " + getEventTitle(time),
            Toast.LENGTH_SHORT
        )
            .show()
    }

    // Функция события при нажатии на занятую ячейку
    override fun onEventClick(event: WeekViewEvent, eventRect: RectF) {
        Toast.makeText(activity, "Clicked $event", Toast.LENGTH_SHORT).show()
    }

    // Функция события при долгом нажатии на занятую ячейку
    override fun onEventLongPress(event: WeekViewEvent, eventRect: RectF) {
        Toast.makeText(activity, "Long pressed event: " + event.name, Toast.LENGTH_SHORT).show()
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
        mWeekView.dayTimeInterpreter = object : WeekView.DayTimeInterpreter {
            override fun interpretDay(date: Int): String {
                return DayOfWeek.of(date).getDisplayName(TextStyle.SHORT, Locale.getDefault())
            }

            override fun interpretTime(hour: Int, minutes: Int): String {
                val strMinutes = String.format(Locale.getDefault(), "%02d", minutes)
                return if (hour == 8) {
                    (if (hour > 23) "8:$strMinutes" else (8 + hour).toString() + ":" + strMinutes)
                } else {
                    (if (hour > 23) "8:$strMinutes" else (8 + hour).toString() + ":" + strMinutes)
                }
            }
        }
    }

    private inner class DragTapListener : View.OnLongClickListener {
        override fun onLongClick(v: View): Boolean {
            val data = ClipData.newPlainText("", "")
            val shadowBuilder = View.DragShadowBuilder(v)
            v.startDragAndDrop(data, shadowBuilder, v, 0)
            return true
        }
    }

    companion object {
        fun newInstance() = UserCalendarFragment()
        private const val TYPE_DAY_VIEW = 1
        private const val TYPE_THREE_DAY_VIEW = 2
        private const val TYPE_WEEK_VIEW = 3
        private val random = Random()

        @ColorInt
        private fun randomColor(): Int {
            return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
        }
    }


}