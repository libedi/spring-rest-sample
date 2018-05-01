<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"></meta>
<title>PDF 테스트</title>
</head>
<body>
	<img src="${imgPath}/bi.png" alt="IMPay CCS"/>
	
	<table border="1">
		#foreach($name in $list)
		<tr>
			<td>$name</td>
		</tr>
		#end
	</table>
	
	테스트입니다 : ${testdata?default("")}
</body>
</html>