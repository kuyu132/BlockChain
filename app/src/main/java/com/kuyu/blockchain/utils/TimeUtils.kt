package com.kuyu.blockchain.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * TimeUtils
 */
object TimeUtils {

        /**
         * 精确到年
         */
        val YEAR_DATE_FORMAT = "yyyy"
        /**
         * 精确到月
         */
        val MONTH_DATE_FORMAT = "yyyy-MM"
        /**
         * 精确到日
         */
        val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
        /**
         * 精确到分
         */
        val MINUTE_DATE_FORMAT = "yyyy-MM-dd HH:mm"
        /**
         * 精确到秒
         */
        val SECOND_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
        /**
         * 文件名使用,精确到毫秒
         */
        val FILE_NAME_DATE_FORMAT = "yyyy_MM_dd_HH_mm_ss_SSS"
        /**
         * 只显示小时和分钟
         */
        val ONLY_HOUR_AND_MINUTE_FORMAT = "HH:mm"
        /**
         * 只显示月份和天数
         */
        val ONLY_MONTH_AND_DAY_FORMAT = "MM-dd"

        /**
         * 一天包含的毫秒数
         */
        val MILLIS_IN_ONE_DAY = 1000 * 60 * 60 * 24

        /**
         * 获取剩余的天数,结束日期应该大于开始日期,否则返回-1
         */
        fun getRemainingDaysCount(beginTimeInMillis: Long, endTimeInMillis: Long): Int {
            var beginTimeInMillis = beginTimeInMillis
            var endTimeInMillis = endTimeInMillis

            if (endTimeInMillis > beginTimeInMillis) {
                val pCalendar = Calendar.getInstance()

                // 设置时间为0时
                pCalendar.timeInMillis = beginTimeInMillis
                pCalendar.set(Calendar.HOUR_OF_DAY, 0)
                pCalendar.set(Calendar.MINUTE, 0)
                pCalendar.set(Calendar.SECOND, 0)
                //获取0时对应的毫秒时间
                beginTimeInMillis = pCalendar.timeInMillis

                pCalendar.timeInMillis = endTimeInMillis
                pCalendar.set(Calendar.HOUR_OF_DAY, 0)
                pCalendar.set(Calendar.MINUTE, 0)
                pCalendar.set(Calendar.SECOND, 0)
                //获取0时对应的毫秒时间
                endTimeInMillis = pCalendar.timeInMillis

                // 得到两个日期相差的天数
                return ((endTimeInMillis - beginTimeInMillis) / MILLIS_IN_ONE_DAY).toInt() + 1
            } else {
                return -1
            }
        }

        /**
         * 判断日期是否同一天
         */
        fun areSameDay(dateA: Date, dateB: Date): Boolean {
            val calDateA = Calendar.getInstance()
            calDateA.time = dateA

            val calDateB = Calendar.getInstance()
            calDateB.time = dateB

            return (calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                    && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                    && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH))
        }

        /**
         * 时间取年月日后,获取间隔天数
         */
        fun getDayInterval(dateA: Date, dateB: Date): Long {
            val fromCalendar = Calendar.getInstance()
            fromCalendar.time = dateA
            fromCalendar.set(Calendar.HOUR_OF_DAY, 0)
            fromCalendar.set(Calendar.MINUTE, 0)
            fromCalendar.set(Calendar.SECOND, 0)
            fromCalendar.set(Calendar.MILLISECOND, 0)

            val toCalendar = Calendar.getInstance()
            toCalendar.time = dateB
            toCalendar.set(Calendar.HOUR_OF_DAY, 0)
            toCalendar.set(Calendar.MINUTE, 0)
            toCalendar.set(Calendar.SECOND, 0)
            toCalendar.set(Calendar.MILLISECOND, 0)

            return (toCalendar.time.time - fromCalendar.time.time) / (1000 * 60 * 60 * 24)
        }

        /**
         * 将日期的时分秒设置为00：00：00
         */
        fun setTime2DayBegin(time: Long): Long {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = time
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MINUTE, 0)
            return calendar.timeInMillis
        }

        /**
         * 将日期的时分秒设置为23：59：59
         */
        fun setTime2DayEnd(time: Long): Long {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = time
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.SECOND, 59)
            calendar.set(Calendar.MINUTE, 59)
            return calendar.timeInMillis
        }

        @JvmOverloads
        fun getTimeStr(date: Date, dateFormat: String = DEFAULT_DATE_FORMAT): String {
            return SimpleDateFormat(dateFormat).format(date)
        }

        @JvmOverloads
        fun getTimeStr(timeInMillis: Long, dateFormat: String = DEFAULT_DATE_FORMAT): String {
            return SimpleDateFormat(dateFormat).format(Date(timeInMillis))
        }

        val currentTimeInLong: Long
            get() = System.currentTimeMillis()

        val currentTimeInString: String
            get() = getTimeStr(currentTimeInLong)

        fun getCurrentTimeInString(dateFormat: String): String {
            return getTimeStr(currentTimeInLong, dateFormat)
        }

        fun getTimeWithYear(mills: Long): String {
            val simpleDateFormat = SimpleDateFormat(YEAR_DATE_FORMAT)
            return simpleDateFormat.format(mills)
        }

        fun getTimeWithMonthAndDay(mills: Long): String {
            val simpleDateFormat = SimpleDateFormat(ONLY_MONTH_AND_DAY_FORMAT)
            return simpleDateFormat.format(mills)
        }

        //return time format by "yyyy-MM--dd HH:mm:ss"
        fun getTimeWithSecond(mills: Long): String {
            val simpleDateFormat = SimpleDateFormat(SECOND_DATE_FORMAT)
            return simpleDateFormat.format(mills)
        }

        //return time format by "yyyy-MM--dd HH:mm" 24h
        fun getTimeWithMin(mills: Long): String {
            val simpleDateFormat = SimpleDateFormat(MINUTE_DATE_FORMAT)
            return simpleDateFormat.format(mills)
        }

        fun getCalendarFromTimeStamp(mills: Long): Calendar {
            val cal = Calendar.getInstance()
            cal.timeInMillis = mills
            return cal
        }

        fun getTimeStamp(timeStr: String, dateFormat: String): Long {
            val simpleDateFormat = SimpleDateFormat(dateFormat)
            try {
                return simpleDateFormat.parse(timeStr)!!.time
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return 0L
        }

        /**
         * 本地获取的时间戳(13位)转化为服务器时间戳(10位)
         */
        fun toServerTimestamp(mills: Long): Long {
            return Math.round(mills / 1000.0)
        }

        /**
         * 获取某个日期的年份
         */
        fun getYear(mills: Long): Int {
            val simpleDateFormat = SimpleDateFormat("yyyy")
            return Integer.parseInt(simpleDateFormat.format(mills))
        }

        /**
         * 获取某个日期的月份
         */
        fun getMonth(mills: Long): Int {
            val simpleDateFormat = SimpleDateFormat("MM")
            return Integer.parseInt(simpleDateFormat.format(mills))
        }

        /**
         * 获取某个日期的日
         */
        fun getDay(mills: Long): Int {
            val simpleDateFormat = SimpleDateFormat("dd")
            return Integer.parseInt(simpleDateFormat.format(mills))
        }

        /**
         * 获取某一天的0点的时间戳
         */
        fun getCurrentStartTimestamp(mills: Long): Long {
            val calendar = Calendar.getInstance()
            calendar.set(getYear(mills), getMonth(mills) - 1, getDay(mills))
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.timeInMillis
        }

        /**
         * 根据当前时间获取当前周开始的时间戳
         */
        fun getCurrentWeekStartTimestamp(mills: Long): Long {
            val date = Date(mills)
            val calendar = Calendar.getInstance()
            calendar.time = date
            var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            if (dayOfWeek == 1) {
                dayOfWeek += 7
            }
            calendar.add(Calendar.DATE, 2 - dayOfWeek)
            return getCurrentStartTimestamp(calendar.timeInMillis)
        }

        /**
         * 根据当前时间获取当前月的开始时间戳
         */
        fun getCurrentMonthStartTimestamp(mills: Long): Long {
            val date = Date(mills)
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(getYear(mills), getMonth(mills) - 1, 1)
            return getCurrentStartTimestamp(calendar.timeInMillis)
        }

        /**
         * 获取当前时区
         */
        val currentTimeZone: String
            get() {
                val tz = TimeZone.getDefault()
                return tz.getDisplayName(false, TimeZone.SHORT)
            }

        fun changeZoneTime(oldTimeZoneStr: String, timeStamp: Long): Long {
            val localTZ = TimeZone.getDefault()
            val oldTimeZone = TimeZone.getTimeZone(oldTimeZoneStr)
            val timeOffset = oldTimeZone.rawOffset - localTZ.rawOffset
            val dateTmp = Date(timeStamp - timeOffset)
            return dateTmp.time
        }
}
