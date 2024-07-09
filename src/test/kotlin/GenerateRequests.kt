import java.io.File
import java.util.*

fun main() {
    // Course data
    val courses = listOf(
        listOf("ALG2-OL", "On Level Algebra 2", "Math"),
        listOf("ALG2-H", "Honors Algebra 2", "Math"),
        listOf("PHY-OL", "On Level Physics", "Science"),
        listOf("PHY-AP1", "AP Physics 1", "Science"),
        listOf("WORLD-OL", "On Level World History", "History"),
        listOf("WORLD-AP", "AP World History", "History"),
        listOf("10LIT-OL", "On Level 10th Lit", "ELA"),
        listOf("10LIT-H", "Honors 10th Lit", "ELA")
    )

    // Lists of first and last names
    val firstNames = listOf(
        "James", "Mary", "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda",
        "William", "Elizabeth", "David", "Barbara", "Richard", "Susan", "Joseph", "Jessica",
        "Thomas", "Sarah", "Charles", "Karen", "Christopher", "Nancy", "Daniel", "Lisa",
        "Matthew", "Betty", "Anthony", "Margaret", "Mark", "Sandra", "Paul", "Ashley",
        "Steven", "Kimberly", "Andrew", "Donna", "Kenneth", "Emily", "Joshua", "Michelle",
        "Kevin", "Carol", "Brian", "Amanda", "George", "Melissa", "Edward", "Deborah",
        "Ronald", "Stephanie", "Timothy", "Rebecca", "Jason", "Laura", "Jeffrey", "Helen",
        "Ryan", "Sharon", "Jacob", "Cynthia", "Gary", "Kathleen", "Nicholas", "Amy",
        "Eric", "Shirley", "Jonathan", "Angela", "Stephen", "Anna", "Larry", "Ruth",
        "Justin", "Brenda", "Scott", "Pamela", "Brandon", "Nicole", "Frank", "Katherine",
        "Benjamin", "Samantha", "Gregory", "Christine", "Raymond", "Catherine", "Samuel",
        "Virginia", "Patrick", "Debra", "Alexander", "Rachel", "Jack", "Janet", "Dennis",
        "Emma", "Jerry", "Carolyn", "Tyler", "Maria"
    )

    val lastNames = listOf(
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
        "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson",
        "Thomas", "Taylor", "Moore", "Jackson", "Martin", "Lee", "Perez", "Thompson",
        "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson", "Walker",
        "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill",
        "Flores", "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell",
        "Mitchell", "Carter", "Roberts", "Gomez", "Phillips", "Evans", "Turner",
        "Diaz", "Parker", "Cruz", "Edwards", "Collins", "Reyes", "Stewart", "Morris",
        "Morales", "Murphy", "Cook", "Rogers", "Gutierrez", "Ortiz", "Morgan", "Cooper",
        "Peterson", "Bailey", "Reed", "Kelly", "Howard", "Ramos", "Kim", "Cox", "Ward",
        "Richardson", "Watson", "Brooks", "Chavez", "Wood", "James", "Bennett", "Gray",
        "Mendoza", "Ruiz", "Hughes", "Price", "Alvarez", "Castillo", "Sanders", "Patel",
        "Myers", "Long", "Ross", "Foster", "Jimenez"
    )

    // Generate 400 student names and IDs
    val randomNames = mutableListOf<String>()
    val studentIds = (0..99).map { it.toString() }

    val random = Random()
    for (i in 0..99) {
        val firstName = firstNames[random.nextInt(firstNames.size)]
        val lastName = lastNames[random.nextInt(lastNames.size)]
        randomNames.add("\"$firstName $lastName\"")
    }

    // Generate student requests
    val studentRequests = mutableListOf<String>()

    for (i in 0..99) {
        val requests = mutableListOf<String>()
        // Randomly select one course from each type
        for (courseType in listOf("Math", "Science", "History", "ELA")) {
            val typeCourses = courses.filter { it[2] == courseType }
            val selectedCourse = typeCourses[random.nextInt(typeCourses.size)]
            requests.add("\"${selectedCourse[0]}\"")
        }
        studentRequests.add("Request(\"\", \"${studentIds[i]}\", ${randomNames[i]}, ${requests[0]}, ${requests[1]}, ${requests[2]}, ${requests[3]}, \"\", \"\", \"\", \"\"),")
    }
    studentRequests.forEach {
        println(it)
    }

}