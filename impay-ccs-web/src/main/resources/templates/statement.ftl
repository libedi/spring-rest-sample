<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<title>${header?default("")}</title>
	<style type="text/css">
		body {margin:0; padding:0; font-size:10pt; font-family:Tahoma, Arial, sans-serif, NanumGothic; color:#333;}
		div.wrap {width:100%; height:100%; margin:0 auto; font-size:10pt;}
		div.wrap div.contents {padding:0 50px;}
		div.wrap table {margin:0 auto;}
		div.contents {}
		div.header {position:relative; width:100%; height:60px; padding:0 0 10px 1px; border: none; border-bottom:4px solid #e51937; }
		div.header img {position:absolute; left:2px; bottom:12px; height:45px;}
		div.under-line {width:100%; height:4px; background:#e51937;}
		div.conts {padding:20px 0; line-height:170%;}
		div.conts table {width:100%;}
		div.conts h2 {height:37px; padding:0; font-size:11pt; font-weight:bold; text-align:left;}
		div.conts h2 img {vertical-align:middle; width:16pt}
		div.conts h2 b {color:#e51937;}
		div.conts p {padding-bottom:30px;}
		div.tbl-box {border-top:2px solid #545454; padding-bottom:30px;}
		table {border-collapse:collapse; border-spacing:0;}
		table th,td {margin:0; padding:0; background:#fff; border:1px solid #c4c4c4; line-height:140%; font-size:8pt}
		table th {background-color:#e8e9eb; padding:7px 3px; border-top:none !important;}
		table td {padding:5px;}
		.text-left {text-align:left;}
		.text-right {text-align:right;}
		.text-center {text-align:center;}
		@media print {
			body {color:#000; background:transprent;}
			.tbl-box {border-top:2px solid #000;}
			th,td {border:1px solid #000;}
			th {background-color:#e8e9eb;}
			p {}
		}
	</style>
</head>
<body>
	<div class="wrap">
		<div class="contents">
			<div class="header">
				<h1><img src="${imgPath?default("")}/bi_impay.png" alt="IMPay"/></h1>
			</div>
			<div class="under-line"></div>
			<div class="conts">
				<p>
					${text1?default("")}
				</p>
				<h2><img src="${imgPath?default("")}/bul_statement.png" alt=""/><b>${mphnNo?default("")}</b> ${title}</h2>
				<div class="tbl-box text-center">
					<table class="result">
						<thead>
							<tr>
								<th style="width:5%">${col1?default("")}</th>
								<th style="width:11%">${col2?default("")}</th>
								<th style="width:11%">${col3?default("")}</th>
								<th style="width:22%">${col4?default("")}</th>
								<th style="width:13%">${col5?default("")}</th>
								<th style="width:19%">${col6?default("")}</th>
								<th style="width:11%">${col7?default("")}</th>
								<th style="width:8%">${col8?default("")}</th>
							</tr>
						</thead>
						<tbody>
							<#list trdList as trd>
							<tr>
								<td>${trd?counter}</td>
								<td>${trd.trdDt?default("")}</td>
								<td>${trd.cnclDt?default("")}</td>
								<td class="text-left">${trd.paySvcNm?default("")}</td>
								<td>${trd.telNo?default("")}</td>
								<td class="text-left">${trd.godsNm?default("")}</td>
								<td class="text-right">${trd.payAmt?default("")}</td>
								<td>${trd.payStat?default("")}</td>
							</tr>
							</#list>
						</tbody>
					</table>
				</div>
				<p>
					${text2?default("")} 
				</p>
			</div>
		</div>
	</div>
</body>
</html>