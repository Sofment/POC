package com.company.report;

/**
 * Created by avsupport on 1/30/15.
 */
public class HtmlPattern {
    public static String HTMLPattern = "<html>" +
            "<body>" +
            "<table border=1>%s</table>" +
            "</body>" +
            "</html>";

    public static String HTMLSingleRawPattern = "<tr>%s</tr>";
    public static String HTMLSingleFailedRawPattern = "<tr bgcolor=\"#FE2E2E\">%s</tr>";
    public static String HTMLSinglePassedRawPattern = "<tr bgcolor=\"#2EFE2E\">%s</tr>";

    public static String HTMLSingleCellPattern = "<td>%s</td>";
    public static String HTMLStyleSingleCellPattern = "<td %s>%s</td>";
    public static String HTMLSingleCellMergeRowPattern = "<td rowspan=\"%s\" align=\"center\">%s</td>";
    public static String HTMLSingleFailedCellPattern = "<td bgcolor=\"#FE2E2E\" align=\"center\">%s</td>";
    public static String HTMLSingleRetestCellPattern = "<td bgcolor=\"#81BEF7\" align=\"center\">%s</td>";
    public static String HTMLSinglePassedCellPattern = "<td bgcolor=\"#2EFE2E\" align=\"center\">%s</td>";

    public static String HTMLTestRailLinkPattern = "<a href=\"https://nook.testrail.com/index.php?/runs/view/%s&group_by=cases:section_id&group_order=asc\">%s</a>";
    public static String HTMLCellHeaderPattern ="<th align=\"center\">%s</th>";
}
