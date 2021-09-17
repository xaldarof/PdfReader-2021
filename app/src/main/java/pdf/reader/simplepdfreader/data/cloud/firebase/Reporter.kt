package pdf.reader.simplepdfreader.data.cloud.firebase


class Reporter(private val reportServiceImpl: ReportServiceImpl) : ReportService {
    override fun sendReport(reportModel: ReportModel) {
        reportServiceImpl.sendReport(reportModel)
    }
}