DELETE FROM BATCH_STEP_EXECUTION_CONTEXT BSEC
WHERE EXISTS (
	SELECT 1 FROM BATCH_STEP_EXECUTION BSE
	WHERE EXISTS (
		SELECT 1 FROM BATCH_JOB_EXECUTION
		WHERE TO_CHAR(CREATE_TIME, 'YYYYMMDD') < TO_CHAR(CURRENT_DATE - 3, 'YYYYMMDD')
		AND BSE.JOB_EXECUTION_ID = JOB_EXECUTION_ID
	)
	AND BSEC.STEP_EXECUTION_ID = BSE.STEP_EXECUTION_ID
);
DELETE FROM BATCH_JOB_EXECUTION_CONTEXT BJEC
WHERE EXISTS (
	SELECT 1 FROM BATCH_JOB_EXECUTION
	WHERE TO_CHAR(CREATE_TIME, 'YYYYMMDD') < TO_CHAR(CURRENT_DATE - 3, 'YYYYMMDD')
	AND BJEC.JOB_EXECUTION_ID = JOB_EXECUTION_ID
);
DELETE FROM BATCH_STEP_EXECUTION BSE
WHERE EXISTS (
	SELECT 1 FROM BATCH_JOB_EXECUTION
	WHERE TO_CHAR(CREATE_TIME, 'YYYYMMDD') < TO_CHAR(CURRENT_DATE - 3, 'YYYYMMDD')
	AND BSE.JOB_EXECUTION_ID = JOB_EXECUTION_ID
);
DELETE FROM BATCH_JOB_EXECUTION_PARAMS BJEP
WHERE EXISTS (
	SELECT 1 FROM BATCH_JOB_EXECUTION
	WHERE TO_CHAR(CREATE_TIME, 'YYYYMMDD') < TO_CHAR(CURRENT_DATE - 3, 'YYYYMMDD')
	AND BJEP.JOB_EXECUTION_ID = JOB_EXECUTION_ID
);
DELETE FROM BATCH_JOB_EXECUTION
WHERE TO_CHAR(CREATE_TIME, 'YYYYMMDD') < TO_CHAR(CURRENT_DATE - 3, 'YYYYMMDD')
;
DELETE FROM BATCH_JOB_INSTANCE BJI
WHERE NOT EXISTS (
	SELECT 1 FROM BATCH_JOB_EXECUTION WHERE BJI.JOB_INSTANCE_ID = JOB_INSTANCE_ID
);

/*
ALTER SEQUENCE BATCH_STEP_EXECUTION_SEQ RESTART WITH 1;
ALTER SEQUENCE BATCH_JOB_EXECUTION_SEQ RESTART WITH 1;
ALTER SEQUENCE BATCH_JOB_SEQ RESTART WITH 1;
*/