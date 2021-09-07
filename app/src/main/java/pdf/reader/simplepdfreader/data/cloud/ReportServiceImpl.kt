package pdf.reader.simplepdfreader.data.cloud

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ReportServiceImpl(private var firebaseDatabase: FirebaseDatabase,private var databaseReference: DatabaseReference) : ReportService {
    override fun sendReport(reportModel: ReportModel) {
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("reports")

        databaseReference.push().setValue(reportModel)
    }
}