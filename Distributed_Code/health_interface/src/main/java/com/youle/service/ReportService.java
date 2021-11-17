package com.youle.service;

import java.util.List;

public interface ReportService {
    List<Integer> findMemberCountByMonth(List<String> month);
}
