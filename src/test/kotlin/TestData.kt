import data.Course
import data.Request
import data.Teacher
import kotlinx.css.li
import org.appchallenge2024.schedule.plugins.HashMapData

data class RawSet(val courses: List<Course>, val requests: List<Request>, val teachers: List<Teacher>)

fun convertRawToMap(courses: List<Course>, requests: List<Request>, teachers: List<Teacher>): HashMapData {
    val coursesMap = hashMapOf<String, Course>()
    courses.forEach {
        coursesMap[it.id] = it
    }
    val requestsMap = hashMapOf<String, Request>()
    requests.forEach {
        requestsMap[it.student_id] = it
    }
    val teachersMap = hashMapOf<String, Teacher>()
    teachers.forEach {
        teachersMap[it.id] = it
    }
    return HashMapData(coursesMap, requestsMap, teachersMap)
}

fun getRaw1(): RawSet {
    val courses = listOf(
        Course("1", "ALG2-OL", "OL Alg 2", "Math"),
        Course("1", "ALG2-H", "H Alg 2", "Math"),
        Course("1", "PHY-OL", "OL Phy", "Science"),
        Course("1", "PHY-AP", "Ap phy", "Science"),
        Course("1", "WORLD-OL", "OL World", "History"),
        Course("1", "WORLD-AP", "Ap world", "History"),
        Course("1", "10LIT-OL", "OL Lit", "ELA"),
        Course("1", "10LIT-H", "H Lit", "ELA")
    )
    val requests = listOf(
        Request("1", "0", "Student 0", "ALG2-H", "PHY-OL", "WORLD-AP", "10LIT-OL"),
        Request("1", "1", "Student 1", "ALG2-OL", "PHY-OL", "WORLD-OL", "10LIT-OL"),
        Request("1", "2", "Student 2", "ALG2-H", "PHY-AP", "WORLD-AP", "10LIT-H"),
        Request("1", "3", "Student 3", "ALG2-OL", "PHY-AP", "WORLD-AP", "10LIT-H"),
        Request("1", "4", "Student 4", "ALG2-OL", "PHY-AP", "WORLD-AP", "10LIT-OL"),
        Request("1", "5", "Student 5", "ALG2-H", "PHY-AP", "WORLD-AP", "10LIT-H"),
        Request("1", "6", "Student 6", "ALG2-H", "PHY-AP", "WORLD-OL", "10LIT-H"),
        Request("1", "7", "Student 7", "ALG2-OL", "PHY-OL", "WORLD-OL", "10LIT-H"),
        Request("1", "8", "Student 8", "ALG2-H", "PHY-OL", "WORLD-OL", "10LIT-OL"),
        Request("1", "9", "Student 9", "ALG2-OL", "PHY-AP", "WORLD-OL", "10LIT-H")
    )
    val teachers = listOf(
        Teacher("1", "0", "Robert (Math)", "100", "3", "Math"),
        Teacher("1", "1", "John (Science)", "101", "4", "Science"),
        Teacher("1", "2", "James (History)", "102", "3", "History"),
        Teacher("1", "3", "Justin (ELA)", "103", "4", "ELA")
    )
    return RawSet(courses, requests, teachers)
}