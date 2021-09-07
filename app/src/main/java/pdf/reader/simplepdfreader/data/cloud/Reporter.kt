package pdf.reader.simplepdfreader.data.cloud


class Reporter(private val reportServiceImpl: ReportServiceImpl) : ReportService{
    override fun sendReport(reportModel: ReportModel) {
        reportServiceImpl.sendReport(reportModel)
    }
}