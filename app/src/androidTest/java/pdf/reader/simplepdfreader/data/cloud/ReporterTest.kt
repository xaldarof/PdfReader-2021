package pdf.reader.simplepdfreader.data.cloud

import com.google.firebase.database.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.google.firebase.database.DataSnapshot
import pdf.reader.simplepdfreader.data.cloud.firebase.ReportModel
import pdf.reader.simplepdfreader.data.cloud.firebase.ReportServiceImpl
import pdf.reader.simplepdfreader.data.cloud.firebase.Reporter


class ReporterTest {

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var reporter: Reporter
    private val testReport =
        ReportModel("This is test report", "testTime:20/20/2003", "testAddress")

    @Before
    fun setUp() {
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("reports")
        reporter = Reporter(ReportServiceImpl(firebaseDatabase, reference))

    }

    @Test
    fun check_is_user_report_sent_to_firebase_database() {
        reporter.sendReport(testReport)
        val list = ArrayList<ReportModel>()
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children) {
                 val model  = data.getValue(ReportModel::class.java)
                    list.add(model!!)
                }

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
        val actual = list.contains(testReport)
        assertEquals(true, actual)
    }
}