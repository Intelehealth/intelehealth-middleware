package com.emrmiddleware.dmo;

import com.emrmiddleware.dto.PatientSyncLogDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PatientSyncLogDMO {

  @Select(
      "SELECT attempt_number, status, "
          + "DATE_FORMAT(COALESCE(completed_at, started_at), '%Y-%m-%d %H:%i:%s') AS last_try "
          + "FROM patient_sync_log "
          + "WHERE patient_uuid = #{patientUuid} "
          + "ORDER BY attempt_number DESC "
          + "LIMIT 1")
  PatientSyncLogDTO getLatestSyncLogByPatientUuid(@Param("patientUuid") String patientUuid);
}
