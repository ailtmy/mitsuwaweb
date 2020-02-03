<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" />
	<xsl:variable name="申請種別" select="/登記申請書/申請書区分/申請種別"/>
	<xsl:variable name="補正有無" select="/登記申請書/制御情報/補正有無='有'"/>
	<!-- 2018-10-01 修正 -->
	<xsl:variable name="移行区分">
		<xsl:choose>
			<xsl:when test="/登記申請書/申請情報/テンプレートバージョン&gt;=8">
				<xsl:value-of select="2"/>
			</xsl:when>
			<xsl:when test="/登記申請書/申請情報/テンプレートバージョン&gt;=7">
				<xsl:value-of select="1"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="0"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<!-- 2018-10-01 修正 EOL -->
	<!--##################################################################-->
	<xsl:template match="登記申請書">
		<HTML>
			<head>
				<META http-equiv="Content-Type" content="text/html;charset=UTF-8" />
				<META http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
				<title>　</title>
				<style type="text/css">
          BODY{ FONT-FAMILY:"ＭＳ ゴシック"; FONT-SIZE:10pt;}
          TABLE{ FONT-FAMILY:"ＭＳ ゴシック"; FONT-SIZE:10pt;}
          SMALL{ FONT-FAMILY:"ＭＳ ゴシック"; FONT-SIZE:8pt;}
          PRE{ FONT-FAMILY:"ＭＳ ゴシック"; FONT-SIZE:10pt;}
          SPAN{ FONT-FAMILY:"ＭＳ ゴシック"; FONT-SIZE:10pt; float:left; word-wrap:break-word;}
          IMG{ vertical-align: -2px; }
        </style>
			</head>
			<BODY>
				<CENTER>
					<!-- 受領通知埋め込み用タグ -->
					<div id="JuryoTsuchi"/>
					<!-- 2015-06-29 修正 CO15-G1-9001 -->
					<xsl:apply-templates select="甲号印刷データ表紙"/>
					<xsl:apply-templates select="申請書作成エラー"/>
					<xsl:apply-templates select="申請書区分"/>
					<xsl:apply-templates select="申請書情報"/>
					<xsl:apply-templates select="補正申請"/>
					<xsl:apply-templates select="委任状"/>
					<xsl:if test="$補正有無 and count(/登記申請書/補正元申請書情報)&gt;0">
						<!-- 2016-06-07 修正 CO15-G1-9001 -->
						<xsl:apply-templates select="/登記申請書/補正申請/課税情報"/>						
						<HR style="border-width: 4px 0px 0px 0px; height: 4px; border-style: solid; border-color: black;"/>
						<!-- 2016-06-07 修正 EOL -->
						<div style="border-width: 1px 1px 1px 1px; width: 100% ; border-style: solid; border-color: black;">
    						<xsl:apply-templates select="補正元申請書情報"/>
    						<xsl:apply-templates select="補正元委任状"/>
						</div>
					</xsl:if>
					<xsl:apply-templates select="制御情報"/>
					<xsl:apply-templates select="申請情報"/>
					<!-- 2015-06-29 修正 EOL -->
				</CENTER>
			</BODY>
		</HTML>
	</xsl:template>
	<!--##################################################################-->
	<xsl:template match="申請書区分">
		<xsl:apply-templates/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="業務区分">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="事件区分">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="申請種類">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="申請種別">
	</xsl:template>
	<!--##################################################################-->
	<xsl:template match="申請書情報">
		<xsl:apply-templates/>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="申請書の種類">
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="申請書タイトル">
		<!-- 2011-02-02 修正 BSTANO0111 -->
		<xsl:param name="手続様式名称"><xsl:value-of select="/登記申請書/申請情報/手続様式名称"/></xsl:param>
		<xsl:if test="not($補正有無)">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%" cellspacing="0">
				<TR>
					<!-- 2011-01-13 修正 ST障害1269 BSTBNS0981 -->
					<xsl:if test="/登記申請書/申請情報/手続ID='HM0501102320001'">
						<TD ALIGN="CENTER">
							<H2>
								<xsl:apply-templates/>（地図訂正申出情報提供用）
							</H2>
						</TD>
					</xsl:if>
					<xsl:if test="/登記申請書/申請情報/手続ID='HM0501250110001' or /登記申請書/申請情報/手続ID='HM0501250120001'">
						<TD ALIGN="CENTER">
							<H2>
								信託目録記録申請書
							</H2>
						</TD>
					</xsl:if>
					<xsl:if test="not(/登記申請書/申請情報/手続ID='HM0501102320001' or /登記申請書/申請情報/手続ID='HM0501250110001' or /登記申請書/申請情報/手続ID='HM0501250120001')">
						<TD ALIGN="CENTER">
							<H2>
								<xsl:apply-templates/>
							</H2>
						</TD>
					</xsl:if>
					<!-- 2011-01-13 修正 EOL -->
				</TR>
				<xsl:if test="contains($手続様式名称,'電子公文書一括取得用')">
					<TR>
						<TD WIDTH="100%" ALIGN="CENTER">
							<h3>－電子公文書一括取得用－</h3>
						</TD>
					</TR>
				</xsl:if>
			</TABLE>
		</xsl:if>
		<xsl:if test="$補正有無">
			<xsl:call-template name="補正申請"/>
			<!-- 2015-07-08 修正 CO15-G1-9001 -->
			<xsl:if test ="count(/登記申請書/補正元申請書情報)=0">
				<HR SIZE="10"/>
			</xsl:if>
			<xsl:if test ="count(/登記申請書/補正元申請書情報)&gt;0">
				<HR style="border-width: 4px 0px 0px 0px; height: 4px; border-style: solid; border-color: black;"/>
			</xsl:if>
			<!-- 2015-07-08 修正 EOL -->
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%" cellspacing="0">
				<TR>
					<xsl:if test="/登記申請書/申請情報/手続ID='HM0501102320001'">
						<!-- 新しい補正書か古い補正書か判定してそれぞれに合ったタイトルを付ける対応 -->
						<!-- 2015-08-31 修正 CO15-G1-9001 -->
						<xsl:if test="count(/登記申請書/補正元申請書情報)=0">
							<TD ALIGN="CENTER">
								<H2>［補正元］
									<xsl:apply-templates/>（地図訂正申出情報提供用）
								</H2>
							</TD>
						</xsl:if>
						<xsl:if test="count(/登記申請書/補正元申請書情報)&gt;0">
							<TD ALIGN="CENTER">
								<H2>補正後申請内容</H2>
							</TD>
						</xsl:if>
						<!-- 2015-08-31 修正 EOL -->
					</xsl:if>
					<xsl:if test="/登記申請書/申請情報/手続ID='HM0501250110001' or /登記申請書/申請情報/手続ID='HM0501250120001'">
						<!-- 新しい補正書か古い補正書か判定してそれぞれに合ったタイトルを付ける対応 -->
						<!-- 2015-08-31 修正 CO15-G1-9001 -->
						<xsl:if test="count(/登記申請書/補正元申請書情報)=0">
							<TD ALIGN="CENTER">
								<H2>［補正元］
									信託目録記録申請書
								</H2>
							</TD>
						</xsl:if>
						<xsl:if test="count(/登記申請書/補正元申請書情報)&gt;0">
							<TD ALIGN="CENTER">
								<H2>補正後申請内容</H2>
							</TD>
						</xsl:if>
						<!-- 2015-08-31 修正 EOL -->
					</xsl:if>
					<xsl:if test="not(/登記申請書/申請情報/手続ID='HM0501102320001' or /登記申請書/申請情報/手続ID='HM0501250110001' or /登記申請書/申請情報/手続ID='HM0501250120001')">
						<!-- 新しい補正書か古い補正書か判定してそれぞれに合ったタイトルを付ける対応 -->
						<!-- 2015-06-29 修正 CO15-G1-9001 -->
						<xsl:if test="count(/登記申請書/補正元申請書情報)=0">
							<TD ALIGN="CENTER">
								<H2>［補正元］
									<xsl:apply-templates/>
								</H2>
							</TD>
						</xsl:if>
						<xsl:if test="count(/登記申請書/補正元申請書情報)&gt;0">
							<TD ALIGN="CENTER">
								<H2>補正後申請内容</H2>
							</TD>
						</xsl:if>
						<!-- 2015-06-29 修正 EOL -->
					</xsl:if>
				</TR>
				<xsl:if test="contains($手続様式名称,'電子公文書一括取得用')">
					<TR>
						<TD WIDTH="100%" ALIGN="CENTER">
							<h3>－電子公文書一括取得用－</h3>
						</TD>
					</TR>
				</xsl:if>
				<xsl:if test="not(contains($手続様式名称,'電子公文書一括取得用'))">
					<TR>
						<TD><br/></TD>
					</TR>
				</xsl:if>
			</TABLE>
		</xsl:if>
		<!-- 2011-02-02 修正 EOL -->
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="申請事項">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>
			<P/>
		</xsl:if>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="申請手続き情報">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>
			<!-- 2011-05-09 CO11-G1-0004-登記完了証の窓口受取対応 -->
			<!-- <HR/> -->
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="添付書類">
		<xsl:if test="not(.='')">
			<xsl:choose>
				<xsl:when test="count(./添付情報)=0 and count(./会社法人等番号付添付情報)=0">
					<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
						<TR>
							<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP">添付情報</TD>
							<TD ALIGN="LEFT" WIDTH="75%"><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></TD>
						</TR>
					</TABLE>
				</xsl:when>
				<xsl:when test="count(./添付情報)&gt;0 and not(./添付情報='')">
					<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
						<TR>
							<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP">添付情報</TD>
							<TD ALIGN="LEFT" WIDTH="75%"><SPAN><xsl:apply-templates select="./添付情報"/></SPAN></TD>
						</TR>
						<xsl:if test="count(./会社法人等番号付添付情報)&gt;0 and count(./会社法人等番号付添付情報/*[.!=''])&gt;0">
							<xsl:apply-templates select="./会社法人等番号付添付情報"/>
						</xsl:if>
					</TABLE>
				</xsl:when>
				<xsl:when test="count(./会社法人等番号付添付情報)&gt;0 and count(./会社法人等番号付添付情報/*[.!=''])&gt;0">
					<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
						<TR>
							<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP">添付情報</TD>
							<TD ALIGN="LEFT" WIDTH="75%"></TD>
						</TR>
						<xsl:apply-templates select="./会社法人等番号付添付情報"/>
					</TABLE>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="添付情報">
		<xsl:call-template name="print-kanji-image"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="会社法人等番号付添付情報">
		<xsl:if test="count(./*[.!=''])&gt;0">
			<TR>
				<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP"></TD>
				<TD ALIGN="LEFT">
					<xsl:apply-templates select="./添付書類名"/>
					<xsl:apply-templates select="./添付書類の会社法人等番号"/>
				</TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="添付書類名">
		<xsl:if test="not(.='')">
			<xsl:call-template name="print-kanji-image"/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="添付書類の会社法人等番号">
		<xsl:if test="not(.='')">
			<xsl:text>(会社法人等番号　</xsl:text>
			<xsl:apply-templates/>
			<xsl:text>）</xsl:text>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="住民票コード情報">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP">住民票コード情報</TD>
					<TD ALIGN="LEFT" WIDTH="75%"><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="申請年月日">
		<xsl:if test="not(./年月日='')">
			<P/>
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%"><xsl:apply-templates/>申請</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="年月日">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="その他規定条項等">
		<xsl:if test="not(.='')">
			　<xsl:apply-templates/>　
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="宛先登記所">
		<xsl:if test="not(./宛先登記所名='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="10%" VALIGN="TOP"></TD>
					<TD ALIGN="LEFT" WIDTH="90%"><xsl:apply-templates/></TD>
				</TR>
			</TABLE>
			<P/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="宛先登記所名">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="登記所コード">
		<xsl:if test="not(.='')">
			（登記所コード：<xsl:apply-templates/>）
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<!-- 2011-05-09 CO11-G1-0004-登記完了証の窓口受取対応 -->
	<xsl:template match="登記完了証の交付方法">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP">登記完了証の交付方法</TD>
					<TD ALIGN="LEFT" WIDTH="75%"><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!-- 2011-05-09 EOL -->
	<!--*****************************************************************-->
	<!--**   申請物件情報                                              **-->
	<!--*****************************************************************-->
	<xsl:template match="申請物件情報">
		<xsl:if test="not(.='')">
			<!-- 2011-05-09 CO11-G1-0004-登記完了証の窓口受取対応 -->
			<HR/>
			<P/>
			<!-- 2011-05-09 EOL -->
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="申請物件">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%" VALIGN="TOP">
						不動産の表示（<xsl:number format="1"/>）
					</TD>
				</TR>
			</TABLE>
			<xsl:apply-templates/>
			<BR/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="物件特定情報">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">　
						<xsl:apply-templates/>
					</TD>
				</TR>
				<TR><TD/></TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="物件区分">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="物件種別">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>　
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="物件状態">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="物件指定">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="地番区域情報">
		<xsl:if test="not(.='')">
			<xsl:call-template name="print-kanji-image"/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="地番家屋番号情報">
		<xsl:if test="not(.='')">　
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="共同担保目録番号">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="共担記号">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="共担番号">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<!--yuefeng 2010/06/17 N000004AP delete start-->
	<!--
	<xsl:template match="他管轄の登記所名">
		<xsl:if test="not(.='')">
			他管轄の登記所名：
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	-->
	<!--yuefeng 2010/06/17 N000004AP delete end-->
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="不動産番号">
		<xsl:if test="not(.='')">
			　不動産番号：<xsl:apply-templates/>
		</xsl:if>
		<xsl:if test="(.='')">
			　不動産番号：－
		</xsl:if>
	</xsl:template>
	<!--****************************************************************-->
	<xsl:template match="土地の表示">
        <!-- 2010-12-16 修正 ST障害1154 BSTANO0073(ST障害1159 BSTANO0074) -->
		<!--<xsl:if test="not(./土地の所在欄/土地の所在='')">-->
        <xsl:if test="not(.='')">
        <!-- 2010-12-16 修正 EOL -->
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="20%" VALIGN="TOP">　土地の表示</TD>
				</TR>
			</TABLE>
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="土地の表示/冠記">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="30%" COLSPAN="2">　　<xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="土地の所在欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<xsl:apply-templates/>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="土地の所在欄/土地の所在">
		<xsl:if test="not(.='')">
			<TD ALIGN="LEFT" WIDTH="30%">　　所　　在</TD>
			<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="土地の表示履歴欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="土地の表示履歴欄/地番">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　地　　番</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="土地の表示履歴欄/地目">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　地　　目</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="土地の表示履歴欄/地積">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　地　　積</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<!-- 2018-10-01 修正 -->
	<xsl:template match="土地の表示/対象登記">
		<xsl:if test="count(./対象登記の順位番号/*[.!=''])&gt;0">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="30%">　対象登記の順位番号</TD>
					<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="土地の表示/対象登記/対象登記の順位番号">
    <xsl:if test="not(./主登記の順位番号='') or not(./同順位付記情報='')">
	    <xsl:if test="not(./主登記の順位番号='')"><xsl:value-of select="./主登記の順位番号"/>番</xsl:if>
	    <xsl:if test="not(./同順位付記情報='')"><xsl:value-of select="./同順位付記情報"/></xsl:if>
	    <br/>
	  </xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<!-- 2018-10-01 修正 EOL -->
	<xsl:template match="備考">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="30%"></TD>
					<TD ALIGN="LEFT" WIDTH="70%">
						<SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="信託目録の表示/表示内容">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="TOP" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="30%" VALIGN="TOP">　信託目録の表示</TD>
					<TD ALIGN="LEFT" WIDTH="70%">
						<SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--****************************************************************-->
	<xsl:template match="建物の表示">
        <!-- 2010-12-16 修正 ST障害1154 BSTANO0073(ST障害1159 BSTANO0074) -->
		<!--<xsl:if test="not(./建物の所在欄/建物の所在/地番区域='')">-->
        <xsl:if test="not(.='')">
        <!-- 2010-12-16 修正 EOL -->
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%" VALIGN="TOP">　建物の表示（主である建物の表示）</TD>
				</TR>
			</TABLE>
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の表示/冠記">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">　　<xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の所在欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の所在欄/建物の所在">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　所　　在</TD>
				<TD ALIGN="LEFT" WIDTH="70%">
					<xsl:apply-templates/>
				</TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の所在/地番区域">
		<xsl:call-template name="print-kanji-image"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の所在/敷地番">
		　<xsl:apply-templates/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の所在/換地等の記載">
		<xsl:if test="not(.='')">
		　<xsl:call-template name="print-kanji-image"/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の家屋番号欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<xsl:apply-templates/>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の家屋番号欄/家屋番号">
		<xsl:if test="not(.='')">
			<TD ALIGN="LEFT" WIDTH="30%">　　家屋番号</TD>
			<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の表示履歴欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の表示履歴欄/冠記">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%" COLSPAN="2">　　<xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の表示履歴欄/種類">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　種　　類</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の表示履歴欄/構造">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　構　　造</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の表示履歴欄/床面積">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　床 面 積</TD>
				<TD ALIGN="LEFT" WIDTH="70%">
          <SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
        </TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!-- 2018-10-01 修正 -->
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の表示履歴欄/対象登記">
		<xsl:if test="count(./対象登記の順位番号/*[.!=''])&gt;0">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　対象登記の順位番号</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の表示履歴欄/対象登記/対象登記の順位番号">
		<xsl:if test="not(./主登記の順位番号='') or not(./同順位付記情報='')">
			<xsl:if test="not(./主登記の順位番号='')"><xsl:value-of select="./主登記の順位番号"/>番</xsl:if>
			<xsl:if test="not(./同順位付記情報='')"><xsl:value-of select="./同順位付記情報"/></xsl:if>
			<br/>
		</xsl:if>
	</xsl:template>
	<!-- 2018-10-01 修正 EOL -->
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="建物の表示履歴欄/備考">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="附属建物の表示欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="30%" COLSPAN="2" VALIGN="TOP">　附属建物の表示</TD>
				</TR>
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="附属建物の表示欄/冠記">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%" COLSPAN="2">　　<xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="附属建物の表示欄/符号">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　符　　号</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="附属建物の表示欄/種類">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　種　　類</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="附属建物の表示欄/構造">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　構　　造</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="附属建物の表示欄/床面積">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　床 面 積</TD>
				<TD ALIGN="LEFT" WIDTH="70%">
          <SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
      </TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="附属建物の表示欄/備考">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--****************************************************************-->
	<xsl:template match="一棟の建物の表示">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%" VALIGN="TOP">　一棟の建物の表示</TD>
				</TR>
			</TABLE>
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="一棟の建物の表示/冠記">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">　　<xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="一棟の所在欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="一棟の所在欄/一棟の所在">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　所　　在</TD>
				<TD ALIGN="LEFT" WIDTH="70%">
					<!-- 2017-07-03 修正 -->
					<xsl:apply-templates select="./地番区域"/>
					<xsl:apply-templates select="./敷地番"/>
					<xsl:apply-templates select="./換地等の記載"/>
					<!-- 2017-07-03 修正 EOL -->
				</TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="一棟の所在/地番区域">
		<xsl:call-template name="print-kanji-image"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="一棟の所在/敷地番">
		　<xsl:apply-templates/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="一棟の所在/換地等の記載">
		<xsl:if test="not(.='')">
			　<xsl:call-template name="print-kanji-image"/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="一棟の建物番号欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<TR>
					<xsl:apply-templates/>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="一棟の建物番号欄/建物番号">
		<xsl:if test="not(.='')">
			<TD ALIGN="LEFT" WIDTH="30%">　　建物の名称</TD>
			<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="一棟の表示履歴欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="一棟の表示履歴欄/構造">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　構　　造</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="一棟の表示履歴欄/床面積">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　床 面 積</TD>
				<TD ALIGN="LEFT" WIDTH="70%">
          <SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
      </TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="一棟の建物の表示/備考">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="30%">　</TD>
					<TD ALIGN="LEFT" WIDTH="70%"><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--****************************************************************-->
	<xsl:template match="専有部分の建物の表示">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%" VALIGN="TOP">　専有部分の建物の表示</TD>
				</TR>
			</TABLE>
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の建物の表示/冠記">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">　　<xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の家屋番号欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の家屋番号欄/専有部分の家屋番号">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　地番区域・家屋番号</TD>
				<TD ALIGN="LEFT" WIDTH="70%">
					<xsl:apply-templates/>
				</TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の家屋番号/地番区域">
		<xsl:call-template name="print-kanji-image"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の家屋番号/家屋番号">
		　<xsl:apply-templates/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の建物番号欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<TR>
					<xsl:apply-templates/>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の建物番号欄/建物の番号">
		<xsl:if test="not(.='')">
			<TD ALIGN="LEFT" WIDTH="30%">　　建物の名称</TD>
			<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の表示履歴欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の表示履歴欄/冠記">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%" COLSPAN="2">　　<xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の表示履歴欄/種類">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　種　　類</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の表示履歴欄/構造">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　構　　造</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の表示履歴欄/床面積">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　床 面 積</TD>
				<!--fangli 2010/08/06 既存障害_Mantis障害管理番号:BG1FSI3491 update start-->
				<!--<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>-->
				<TD ALIGN="LEFT" WIDTH="70%">
          <SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
      </TD>
				<!--fangli 2010/08/06 既存障害_Mantis障害管理番号:BG1FSI3491 update end-->
			</TR>
		</xsl:if>
	</xsl:template>
	<!-- 2018-10-01 修正 -->
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の表示履歴欄/対象登記">
		<xsl:if test="count(./対象登記の順位番号/*[.!=''])&gt;0">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　対象登記の順位番号</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の表示履歴欄/対象登記/対象登記の順位番号">
		<xsl:if test="not(./主登記の順位番号='') or not(./同順位付記情報='')">
			<xsl:if test="not(./主登記の順位番号='')"><xsl:value-of select="./主登記の順位番号"/>番</xsl:if>
			<xsl:if test="not(./同順位付記情報='')"><xsl:value-of select="./同順位付記情報"/></xsl:if>
			<br/>
		</xsl:if>
	</xsl:template>
	<!-- 2018-10-01 修正 EOL -->
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="専有部分の表示履歴欄/備考">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="敷地権の表示欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="30%" COLSPAN="2" VALIGN="TOP">　敷地権の表示</TD>
				</TR>
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="敷地権の表示欄/土地の符号">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　符　　号</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="敷地権の表示欄/所在及び地番">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　所在及び地番</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="敷地権の表示欄/地目">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　地　　目</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="敷地権の表示欄/地積">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　地　　積</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="敷地権の表示欄/敷地権の種類">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　敷地権の種類</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="敷地権の表示欄/敷地権の割合">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　敷地権の割合</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="敷地権の表示欄/備考">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--****************************************************************-->
	<xsl:template match="共同担保">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%" VALIGN="TOP">　共同担保</TD>
				</TR>
			</TABLE>
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="共同担保/冠記">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">　　<xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="共同担保目録">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="30%">　　共同担保目録</TD>
					<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="共同担保/事項内容">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="30%">　</TD>
					<TD ALIGN="LEFT" WIDTH="70%"><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--yuefeng 2010/06/17 N000004AP add start-->
	<!--*****************************************************************-->
	<!--**   他管轄物件情報                                            **-->
	<!--*****************************************************************-->
	<xsl:template match="他管轄物件情報">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄物件">
		<xsl:variable name="REP"><xsl:number level="any" count="申請書情報/申請物件情報/申請物件" format="1"/></xsl:variable>
		<!-- 2015-08-25 修正 CO15-G1-9001 -->
		<xsl:variable name="HOSEIMOTO_REP"><xsl:number level="any" count="補正元申請書情報/申請物件情報/申請物件" format="1"/></xsl:variable>
		<!-- 2015-08-25 修正 EOL -->
		<xsl:variable name="Vcnt"><xsl:number format="1"/></xsl:variable>
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%" VALIGN="TOP">
						<!-- 2015-08-25 修正 CO15-G1-9001 -->
						<xsl:choose>
							<xsl:when test="name(../../.)='補正元申請書情報'">
								不動産の表示（<xsl:copy-of select="$HOSEIMOTO_REP + $Vcnt"/>）
							</xsl:when>
							<xsl:otherwise>
						不動産の表示（<xsl:copy-of select="$REP + $Vcnt"/>）
							</xsl:otherwise>
						</xsl:choose>
						<!-- 2015-08-25 修正 EOL -->
					</TD>
				</TR>
			</TABLE>
			<xsl:apply-templates/>
			<BR/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄物件特定情報">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">
						<xsl:apply-templates/>
					</TD>
				</TR>
				<TR><TD/></TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄物件区分">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄物件種別">
		<xsl:if test="not(.='')">　
			<xsl:apply-templates/>　
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄物件状態">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄物件指定">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄地番区域情報">
		<xsl:if test="not(.='')">
			<xsl:call-template name="print-kanji-image"/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄地番家屋番号情報">
		<xsl:if test="not(.='')">　
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄不動産番号">
		<xsl:if test="not(.='')">
			　不動産番号：<xsl:apply-templates/>
		</xsl:if>
		<xsl:if test="(.='')">
			　不動産番号：－
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄の登記所名">
		<xsl:if test="not(.='')">　
			他管轄の登記所名：
			<xsl:call-template name="print-kanji-image"/>
		</xsl:if>
	</xsl:template>
	<!--****************************************************************-->
	<xsl:template match="他管轄土地の表示">
        <!-- 2010-12-16 修正 ST障害1154 BSTANO0073(ST障害1159 BSTANO0074) -->
		<!--<xsl:if test="not(./他管轄土地の所在欄/他管轄土地の所在='')">-->
        <xsl:if test="not(.='')">
        <!-- 2010-12-16 修正 EOL -->
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="20%" VALIGN="TOP">　土地の表示</TD>
				</TR>
			</TABLE>
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄土地の表示/他管轄冠記">
        <!-- 2010-12-16 修正 ST障害1154 BSTANO0073(ST障害1159 BSTANO0074) -->
		<!-- <xsl:if test="not(.='')">-->
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<!--<xsl:if test="(.='前登記の表示')">-->
					<TR>
						<TD ALIGN="LEFT" WIDTH="30%" COLSPAN="2">　管轄外の物件</TD>
					</TR>
				<!--</xsl:if>-->
                <xsl:if test="not(.='')">
				<TR>
					<TD ALIGN="LEFT" WIDTH="30%" COLSPAN="2">　　<xsl:apply-templates/></TD>
				</TR>
                </xsl:if>
			</TABLE>
		<!--</xsl:if>-->
        <!-- 2010-12-16 修正 EOL -->
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄土地の所在欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<xsl:apply-templates/>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄土地の所在欄/他管轄土地の所在">
		<xsl:if test="not(.='')">
			<TD ALIGN="LEFT" WIDTH="30%">　　所　　在</TD>
			<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄土地の表示履歴欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄土地の表示履歴欄/他管轄地番">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　地　　番</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄土地の表示履歴欄/他管轄地目">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　地　　目</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄土地の表示履歴欄/他管轄地積">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　地　　積</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄備考">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="30%"></TD>
					<TD ALIGN="LEFT" WIDTH="70%">
						<SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--****************************************************************-->
	<xsl:template match="他管轄建物の表示">
        <!-- 2010-12-16 修正 ST障害1154 BSTANO0073(ST障害1159 BSTANO0074) -->
		<!--<xsl:if test="not(./他管轄建物の所在欄/他管轄建物の所在/他管轄地番区域='')">-->
        <xsl:if test="not(.='')">
        <!-- 2010-12-16 修正 EOL -->
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%" VALIGN="TOP">　建物の表示（主である建物の表示）</TD>
				</TR>
			</TABLE>
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄建物の表示/他管轄冠記">
        <!-- 2010-12-16 修正 ST障害1154 BSTANO0073(ST障害1159 BSTANO0074) -->
		<!-- <xsl:if test="(.='前登記の表示')">-->
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">　管轄外の物件</TD>
				</TR>
                <xsl:if test="not(.='')">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">　　<xsl:apply-templates/></TD>
				</TR>
                </xsl:if>
			</TABLE>
		<!--</xsl:if>-->
        <!-- 2010-12-16 修正 EOL -->
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄建物の所在欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄建物の所在欄/他管轄建物の所在">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　所　　在</TD>
				<TD ALIGN="LEFT" WIDTH="70%">
					<xsl:apply-templates/>
				</TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄建物の所在/他管轄地番区域">
		<xsl:call-template name="print-kanji-image"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄建物の所在/他管轄敷地番">
		　<xsl:apply-templates/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄建物の所在/他管轄換地等の記載">
		<xsl:if test="not(.='')">
			　<xsl:call-template name="print-kanji-image"/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄建物の家屋番号欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<xsl:apply-templates/>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄建物の家屋番号欄/他管轄家屋番号">
		<xsl:if test="not(.='')">
			<TD ALIGN="LEFT" WIDTH="30%">　　家屋番号</TD>
			<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄建物の表示履歴欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄建物の表示履歴欄/他管轄種類">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　種　　類</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄建物の表示履歴欄/他管轄構造">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　構　　造</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄建物の表示履歴欄/他管轄床面積">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　床 面 積</TD>
				<TD ALIGN="LEFT" WIDTH="70%">
          <SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
      </TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄建物の表示履歴欄/他管轄備考">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄附属建物の表示欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="30%" COLSPAN="2" VALIGN="TOP">　附属建物の表示</TD>
				</TR>
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄附属建物の表示欄/他管轄符号">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　符　　号</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄附属建物の表示欄/他管轄種類">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　種　　類</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄附属建物の表示欄/他管轄構造">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　構　　造</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄附属建物の表示欄/他管轄床面積">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　床 面 積</TD>
				<TD ALIGN="LEFT" WIDTH="70%">
          <SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
      </TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄附属建物の表示欄/他管轄備考">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--****************************************************************-->
	<xsl:template match="他管轄一棟の建物の表示">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%" VALIGN="TOP">　一棟の建物の表示</TD>
				</TR>
			</TABLE>
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄一棟の建物の表示/他管轄冠記">
        <!-- 2010-12-16 修正 ST障害1154 BSTANO0073(ST障害1159 BSTANO0074) -->
		<!-- <xsl:if test="(.='前登記の表示')">-->
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">　管轄外の物件</TD>
				</TR>
                <xsl:if test="not(.='')">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">　　<xsl:apply-templates/></TD>
				</TR>
                </xsl:if>
			</TABLE>
		<!--</xsl:if>-->
        <!-- 2010-12-16 修正 EOL -->
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄一棟の所在欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄一棟の所在欄/他管轄一棟の所在">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　所　　在</TD>
				<TD ALIGN="LEFT" WIDTH="70%">
					<!-- 2017-07-03 修正 -->
					<xsl:apply-templates select="./他管轄地番区域"/>
					<xsl:apply-templates select="./他管轄敷地番"/>
					<xsl:apply-templates select="./他管轄換地等の記載"/>
					<!-- 2017-07-03 修正 EOL -->
				</TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄一棟の所在/他管轄地番区域">
		<xsl:call-template name="print-kanji-image"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄一棟の所在/他管轄敷地番">
		　<xsl:apply-templates/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄一棟の所在/他管轄換地等の記載">
		<xsl:if test="not(.='')">
			　<xsl:call-template name="print-kanji-image"/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄一棟の建物番号欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<TR>
					<xsl:apply-templates/>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄一棟の建物番号欄/他管轄建物番号">
		<xsl:if test="not(.='')">
			<TD ALIGN="LEFT" WIDTH="30%">　　建物の名称</TD>
			<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄一棟の表示履歴欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄一棟の表示履歴欄/他管轄構造">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　構　　造</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄一棟の表示履歴欄/他管轄床面積">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　床 面 積</TD>
				<TD ALIGN="LEFT" WIDTH="70%">
          <SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
      </TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄一棟の建物の表示/他管轄備考">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="30%">　</TD>
					<TD ALIGN="LEFT" WIDTH="70%"><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--****************************************************************-->
	<xsl:template match="他管轄専有部分の建物の表示">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%" VALIGN="TOP">　専有部分の建物の表示</TD>
				</TR>
			</TABLE>
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄専有部分の建物の表示/他管轄冠記">
        <!-- 2010-12-16 修正 ST障害1154 BSTANO0073(ST障害1159 BSTANO0074) -->
		<!-- <xsl:if test="(.='前登記の表示')">-->
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">　管轄外の物件</TD>
				</TR>
                <xsl:if test="not(.='')">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">　　<xsl:apply-templates/></TD>
				</TR>
                </xsl:if>
			</TABLE>
		<!--</xsl:if>-->
        <!-- 2010-12-16 修正 EOL -->
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄専有部分の家屋番号欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄専有部分の家屋番号欄/他管轄専有部分の家屋番号">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　地番区域・家屋番号</TD>
				<TD ALIGN="LEFT" WIDTH="70%">
					<xsl:apply-templates/>
				</TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄専有部分の家屋番号/他管轄地番区域">
		<xsl:call-template name="print-kanji-image"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄専有部分の家屋番号/他管轄家屋番号">
		　<xsl:apply-templates/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄専有部分の建物番号欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<TR>
					<xsl:apply-templates/>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄専有部分の建物番号欄/他管轄建物の番号">
		<xsl:if test="not(.='')">
			<TD ALIGN="LEFT" WIDTH="30%">　　建物の名称</TD>
			<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄専有部分の表示履歴欄">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" CELLPADDING="1" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄専有部分の表示履歴欄/他管轄種類">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　種　　類</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:apply-templates/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄専有部分の表示履歴欄/他管轄構造">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　構　　造</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><xsl:call-template name="print-kanji-image"/></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄専有部分の表示履歴欄/他管轄床面積">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　　床 面 積</TD>
				<TD ALIGN="LEFT" WIDTH="70%">
          <SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
      </TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="他管轄専有部分の表示履歴欄/他管轄備考">
		<xsl:if test="not(.='')">
			<TR>
				<TD ALIGN="LEFT" WIDTH="30%">　</TD>
				<TD ALIGN="LEFT" WIDTH="70%"><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></TD>
			</TR>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<!--yuefeng 2010/06/17 N000004AP add end-->
	<!--##################################################################-->
	<!--##　　　　補正書情報　　　　　　　　　　　　　　　　　　　　　　##-->
	<!--##################################################################-->
	<xsl:template match="補正申請">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template name="補正申請">
		<xsl:if test="$補正有無">
			<H2>登記補正書</H2>
			<xsl:apply-templates select="/登記申請書/補正申請/受付年月日"/>
			<xsl:apply-templates select="/登記申請書/補正申請/対象受付番号"/>
			<xsl:apply-templates select="/登記申請書/補正申請/補正対象"/>
			<!-- 2016-06-07 修正 CO15-G1-9001 -->
			<xsl:if test="count(/登記申請書/補正元申請書情報)=0">
				<xsl:apply-templates select="/登記申請書/補正申請/課税情報"/>
			</xsl:if>
			<!-- 2016-06-07 修正 EOL -->
			<xsl:apply-templates select="/登記申請書/補正申請/補正年月日"/>
			<xsl:apply-templates select="/登記申請書/補正申請/申請人"/>
			<xsl:apply-templates select="/登記申請書/補正申請/代理人"/>
			<!-- 2015-06-29 修正 CO15-G1-9001 -->
			<xsl:if test="count(/登記申請書/補正元申請書情報)=0">
				<xsl:apply-templates select="/登記申請書/補正申請/補正事項"/>
			</xsl:if>
			<xsl:if test="count(/登記申請書/補正元申請書情報)&gt;0">
				<xsl:apply-templates select="/登記申請書/補正申請/連絡事項"/>
			</xsl:if>
			<!-- 2015-06-29 修正 EOL -->
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正申請/受付年月日">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%" cellspacing="0">
				<TR ALIGN="LEFT">
					<TD WIDTH="25%">補正の対象</TD>
					<TD WIDTH="75%"><xsl:apply-templates/>受付</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="対象受付番号">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%" cellspacing="0">
				<TR ALIGN="LEFT">
					<TD WIDTH="25%">　</TD>
					<TD WIDTH="75%">第<xsl:apply-templates/>号の登記申請書</TD>
				</TR>
				<TR ALIGN="LEFT"/>
				<TR ALIGN="LEFT">
					<TD WIDTH="25%" VALIGN="TOP" COLSPAN="2">上記の登記の申請について，次のとおり補正する。</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正対象">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="課税情報">
		<xsl:if test="not(./補正納付額='')">
			<TABLE ALIGN="CENTER" BORDER="1" RULES="NONE" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP">登録免許税の追加納付</TD>
					<TD ALIGN="LEFT" WIDTH="75%"><xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正申請/課税情報/課税価格">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正申請/課税情報/課税価格/課税価格合計額">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="40%">課税価格合計額</TD>
					<TD ALIGN="LEFT" WIDTH="60%">金
						<xsl:value-of select="format-number(translate(.,',',''),'###,##0')"/> 円
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正申請/課税情報/登録免許税">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正申請/課税情報/登録免許税/登録免許税合計額">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="40%">登録免許税合計額</TD>
					<TD ALIGN="LEFT" WIDTH="60%">金
						<xsl:value-of select="format-number(translate(.,',',''),'###,##0')"/> 円
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正申請/課税情報/登録免許税/登録免許税適用条項">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="40%">登録免許税適用条項</TD>
					<TD ALIGN="LEFT" WIDTH="60%">
						<xsl:call-template name="print-kanji-image"/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="既納付額">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="40%">既　納　付　額</TD>
					<TD ALIGN="LEFT" WIDTH="60%">金
						<xsl:value-of select="format-number(translate(.,',',''),'###,##0')"/> 円
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正納付額">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="40%">補 正 納 付 額</TD>
					<TD ALIGN="LEFT" WIDTH="60%">金
						<xsl:value-of select="format-number(translate(.,',',''),'###,##0')"/> 円
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正年月日">
		<xsl:if test="not(.='')">
			<P/>
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%" cellspacing="0">
				<TR ALIGN="LEFT">
					<TD WIDTH="35%" COLSPAN="2"><xsl:call-template name="print-kanji-image"/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正申請/申請人">
	<!-- 2015-09-14 修正 CO15-G1-9001 -->
	<xsl:if test="count(/登記申請書/補正元申請書情報)=0">
		<xsl:if test="not(./名義人情報/氏='')">
			<TABLE ALIGN="CENTER" BORDER="0" RULES="NONE" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP">　補正人</TD>
					<TD ALIGN="LEFT" WIDTH="75%"><xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:if>
	<xsl:if test="count(/登記申請書/補正元申請書情報)&gt;0">
	</xsl:if>
	<!-- 2015-09-14 修正 EOL -->
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正申請/代理人">
	<!-- 2015-09-14 修正 CO15-G1-9001 -->
	<xsl:if test="count(/登記申請書/補正元申請書情報)=0">
		<xsl:if test="not(./名義人情報/氏='')">
			<TABLE ALIGN="CENTER" BORDER="0" RULES="NONE" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP">　代理人</TD>
					<TD ALIGN="LEFT" WIDTH="75%"><xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:if>
	<xsl:if test="count(/登記申請書/補正元申請書情報)&gt;0">
	</xsl:if>
	<!-- 2015-09-14 修正 EOL -->
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正事項">
		<xsl:if test="not(.='')">
			<P/>
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR ALIGN="LEFT">
					<TD WIDTH="25%" VALIGN="TOP">補正事項</TD>
					<TD WIDTH="75%">
						<SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!-- 2015-06-29 修正 CO15-G1-9001 -->
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<!-- 連絡事項は汎用的な名前のため補正申請/連絡事項にした -->
	<xsl:template match="補正申請/連絡事項">
		<xsl:if test="not(.='')">
			<P/>
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR ALIGN="LEFT">
					<TD WIDTH="25%" VALIGN="TOP">連絡事項</TD>
					<TD WIDTH="75%">
						<SPAN>
							<xsl:call-template name="print-kanji-image"/>
						</SPAN>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!-- 2015-06-29 修正 EOL -->
	<!--****************************************************************-->
	<xsl:template match="制御情報">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="申請情報">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="業務制御情報">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="プログラムバージョン">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="追加選択1">
	</xsl:template>
	<!--##################################################################-->
	<!--##　　　　委任状情報　　　　　　　　　　　　　　　　　　　　　　##-->
	<!--##################################################################-->
	<xsl:template match="委任状">
		<xsl:if test="not(/登記申請書/制御情報/委任状有無='')">
			<P/>
			<HR/>
			<H2>委　任　状</H2>
			<TABLE ALIGN="CENTER" BORDER="0" RULES="NONE" WIDTH="100%" cellspacing="0">
				<TR>
					<TD WIDTH="20%" ALIGN="LEFT" VALIGN="TOP">　代理人</TD>
					<TD WIDTH="75%" ALIGN="LEFT">
						<xsl:apply-templates select="/登記申請書/申請書情報/申請手続き情報/代理人" mode="表示"/>
					</TD>
				</TR>
				<TR>
					<TD WIDTH="20%" ALIGN="LEFT" VALIGN="TOP"></TD>
					<TD WIDTH="75%" ALIGN="LEFT">
						<xsl:apply-templates select="/登記申請書/申請書情報/申請手続き情報/申請人兼義務者代理人" mode="表示"/>
					</TD>
				</TR>
				<TR>
					<TD WIDTH="20%" ALIGN="LEFT" VALIGN="TOP"></TD>
					<TD WIDTH="75%" ALIGN="LEFT">
						<xsl:apply-templates select="/登記申請書/申請書情報/申請手続き情報/権利者代理人" mode="表示"/>
					</TD>
				</TR>
				<TR>
					<TD WIDTH="20%" ALIGN="LEFT" VALIGN="TOP"></TD>
					<TD WIDTH="75%" ALIGN="LEFT">
						<xsl:apply-templates select="/登記申請書/申請書情報/申請手続き情報/義務者代理人" mode="表示"/>
					</TD>
				</TR>
				<TR><TD WIDTH="20%" ALIGN="LEFT">　</TD><TD WIDTH="75%" ALIGN="LEFT">　</TD></TR>
				<TR>
					<TD WIDTH="20%" ALIGN="LEFT" COLSPAN="2">上記の者を代理人と定め，次の事項を委任します。</TD>
				</TR>
			</TABLE>
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="/登記申請書/申請書情報/申請手続き情報/代理人" mode="表示">
		<xsl:call-template name="代理人表示"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="/登記申請書/申請書情報/申請手続き情報/申請人兼義務者代理人" mode="表示">
		<xsl:call-template name="代理人表示"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="/登記申請書/申請書情報/申請手続き情報/権利者代理人" mode="表示">
		<xsl:call-template name="代理人表示"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="/登記申請書/申請書情報/申請手続き情報/義務者代理人" mode="表示">
		<xsl:call-template name="代理人表示"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="委任事項">
		<xsl:if test="not(.='')">
			<P/>
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%" cellspacing="0">
				<TR ALIGN="LEFT">
					<TD WIDTH="100%" >　委任事項</TD>
				</TR>
				<TR ALIGN="LEFT">
					<TD WIDTH="100%">
						<SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="委任年月日">
		<xsl:if test="not(.='')">
			<P/>
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%" cellspacing="0">
				<TR ALIGN="LEFT">
					<TD WIDTH="25%" COLSPAN="2"><xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="委任者">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" RULES="NONE" WIDTH="100%" cellspacing="0">
				<TR>
					<TD ALIGN="LEFT" WIDTH="20%" VALIGN="TOP">　委任者</TD>
					<TD ALIGN="LEFT" WIDTH="75%">
						<xsl:apply-templates/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template name="代理人表示">
			<TABLE ALIGN="LEFT" BORDER="0" WIDTH="100%" cellspacing="0">
				<xsl:for-each select="child::*">
					<TR>
						<TD WIDTH="100%" ALIGN="LEFT" VALIGN="TOP">
							<xsl:apply-templates/>
						</TD>
					</TR>
				</xsl:for-each>
			</TABLE>
	</xsl:template>
	<!-- 2015-07-08 修正 CO15-G1-9001 -->
	<!--##################################################################-->
	<!--##　　　　補正元申請書情報　　　　　　　　　　　　　　　　　　　##-->
	<!--##################################################################-->
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正元申請書情報">
		<xsl:apply-templates/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正元申請書情報/申請書タイトル">
		<xsl:variable name="手続様式名称"><xsl:value-of select="/登記申請書/補正元申請書情報/手続様式名称"/></xsl:variable>
		<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%" cellspacing="0">
			<TR>
				<xsl:if test="/登記申請書/申請情報/手続ID='HM0501102320001'">
					<TD ALIGN="CENTER">
						<H2>
							［補正元］
							<xsl:apply-templates/>（地図訂正申出情報提供用）
						</H2>
					</TD>
				</xsl:if>
				<xsl:if test="/登記申請書/申請情報/手続ID='HM0501250110001' or /登記申請書/申請情報/手続ID='HM0501250120001'">
					<TD ALIGN="CENTER">
						<H2>
							［補正元］
							信託目録記録申請書
						</H2>
					</TD>
				</xsl:if>
				<xsl:if test="not(/登記申請書/申請情報/手続ID='HM0501102320001' or /登記申請書/申請情報/手続ID='HM0501250110001' or /登記申請書/申請情報/手続ID='HM0501250120001')">
					<TD ALIGN="CENTER">
						<H2>
							［補正元］
							<xsl:apply-templates/>
						</H2>
					</TD>
				</xsl:if>
			</TR>
			<xsl:if test="contains($手続様式名称,'電子公文書一括取得用')">
				<TR>
					<TD WIDTH="100%" ALIGN="CENTER">
						<h3>－電子公文書一括取得用－</h3>
					</TD>
				</TR>
			</xsl:if>
			<xsl:if test="not(contains($手続様式名称,'電子公文書一括取得用'))">
				<TR>
					<TD>
						<br/>
					</TD>
				</TR>
			</xsl:if>
		</TABLE>
	</xsl:template>
	<!--##################################################################-->
	<!--##　　　　補正元委任状　　　　　　　　　　　　　　　　　　　　　##-->
	<!--##################################################################-->
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="補正元委任状">
		<xsl:if test="count(/登記申請書/補正元委任状/委任年月日)&gt;0 and not(/登記申請書/補正元委任状/委任年月日='')">
			<P/>
			<HR/>
			<H2>委　任　状</H2>
			<TABLE ALIGN="CENTER" BORDER="0" RULES="NONE" WIDTH="100%" cellspacing="0">
				<TR>
					<TD WIDTH="20%" ALIGN="LEFT" VALIGN="TOP">　代理人</TD>
					<TD WIDTH="75%" ALIGN="LEFT">
						<xsl:apply-templates select="/登記申請書/補正元申請書情報/申請手続き情報/代理人" mode="表示"/>
					</TD>
				</TR>
				<TR>
					<TD WIDTH="20%" ALIGN="LEFT" VALIGN="TOP"></TD>
					<TD WIDTH="75%" ALIGN="LEFT">
						<xsl:apply-templates select="/登記申請書/補正元申請書情報/申請手続き情報/申請人兼義務者代理人" mode="表示"/>
					</TD>
				</TR>
				<TR>
					<TD WIDTH="20%" ALIGN="LEFT" VALIGN="TOP"></TD>
					<TD WIDTH="75%" ALIGN="LEFT">
						<xsl:apply-templates select="/登記申請書/補正元申請書情報/申請手続き情報/権利者代理人" mode="表示"/>
					</TD>
				</TR>
				<TR>
					<TD WIDTH="20%" ALIGN="LEFT" VALIGN="TOP"></TD>
					<TD WIDTH="75%" ALIGN="LEFT">
						<xsl:apply-templates select="/登記申請書/補正元申請書情報/申請手続き情報/義務者代理人" mode="表示"/>
					</TD>
				</TR>
				<TR><TD WIDTH="20%" ALIGN="LEFT">　</TD><TD WIDTH="75%" ALIGN="LEFT">　</TD></TR>
				<TR>
					<TD WIDTH="20%" ALIGN="LEFT" COLSPAN="2">上記の者を代理人と定め，次の事項を委任します。</TD>
				</TR>
			</TABLE>
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="/登記申請書/補正元申請書情報/申請手続き情報/代理人" mode="表示">
		<xsl:call-template name="代理人表示"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="/登記申請書/補正元申請書情報/申請手続き情報/申請人兼義務者代理人" mode="表示">
		<xsl:call-template name="代理人表示"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="/登記申請書/補正元申請書情報/申請手続き情報/権利者代理人" mode="表示">
		<xsl:call-template name="代理人表示"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="/登記申請書/補正元申請書情報/申請手続き情報/義務者代理人" mode="表示">
		<xsl:call-template name="代理人表示"/>
	</xsl:template>
	<!-- 2015-07-08 修正 EOL -->
	<!--##################################################################-->
	<!--### 一般項目要素                                               ###-->
	<!--##################################################################-->
	<xsl:template name="General_TAG">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP">
						<xsl:choose>
							<xsl:when test="local-name()='信託原簿番号'">信託目録番号</xsl:when>
							<xsl:when test="local-name()='地代及び支払期'">地代及び支払時期</xsl:when>
							<xsl:when test="local-name()='登記原因及びその日付'">原因及びその日付</xsl:when>
							<xsl:otherwise><xsl:value-of select="local-name()"/></xsl:otherwise>
						</xsl:choose>
					</TD>
					<TD ALIGN="LEFT" WIDTH="75%">
					<!-- 2018-10-01 修正 -->
						<xsl:choose>
							<xsl:when test="local-name()='抹消すべき登記' and $移行区分&gt;=2">
								<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
									<xsl:apply-templates/>
								</TABLE>
							</xsl:when>
							<xsl:otherwise><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></xsl:otherwise>
						</xsl:choose>
					<!-- 2018-10-01 修正 EOL -->
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!-- 2018-10-01 修正 -->
	<!--*****************************************************************-->
	<xsl:template match="受付情報">
		<TR>
			<TD ALIGN="LEFT">
				<xsl:apply-templates/>
			</TD>
		</TR>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="受付情報/受付年月日">
	<xsl:if test="not(./元号='') or not(./年='') or not(./月='') or not(./日='')">
		<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
			<TR>
				<TD ALIGN="LEFT">
					<xsl:if test="not(./元号='')"><xsl:value-of select="./元号"/></xsl:if>
					<xsl:if test="not(./年='')"><xsl:value-of select="./年"/>年</xsl:if>
					<xsl:if test="not(./月='')"><xsl:value-of select="./月"/>月</xsl:if>
					<xsl:if test="not(./日='')"><xsl:value-of select="./日"/>日</xsl:if>
					受付
				</TD>
			</TR>
		</TABLE>
	</xsl:if>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="受付情報/受付番号">
		<xsl:if test="not(.='')">
			  <TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						第 <xsl:value-of select="."/> 号
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!-- 2018-10-01 修正 EOL -->
	<!--*****************************************************************-->
	<xsl:template match="登記の目的"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="原因"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="特約"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="売買代金"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="契約費用"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="期間"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="目的"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="存続期間"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="地代"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="支払時期"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="範囲"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="移転持分"><xsl:call-template name="General_TAG"/></xsl:template>
	<!--2014-01-06 CO13-G1-0025 修正-->
	<xsl:template match="移転すべき登記"><xsl:call-template name="General_TAG"/></xsl:template>
	<!--2014-01-06 CO13-G1-0025 EOL-->
	<xsl:template match="小作料"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="代位原因"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="債権額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="工事費用予算額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="利息"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="代位弁済額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="損害金"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="存続期間"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="債権価格"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="極度額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="債権の範囲"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="確定期日"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="担保限度額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="施業方法"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="社債の総額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="社債の利率"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="社債分割発行回数"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="社債の利率の最高限度"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="社債発行金額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="譲渡額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="弁済額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="競売代価"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="延滞利息"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="分割後の債権"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="更正後の順位"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="質入債権"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="優先の定め"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="賃料"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="管理人"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="嘱託条項"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="延滞税の額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="内容"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="採石料"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="利息発生期"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="禁止事項"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="地役権の存続する部分"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="登記原因"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="登記原因及びその日付"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="権利消滅の定め"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="地上権消滅の定め"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="対価"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="譲渡債権額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="変更後の順位"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="遅延損害金"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="元本確定期日"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="債権の条件"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="元本の弁済期"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="元本の支払場所"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="利息の弁済期"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="利息の支払場所"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="権利消滅事項特約"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="賃借権の消滅事項の定め"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="順位譲渡の原因"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="順位放棄の原因"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="回復の原因"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="地代及び支払期"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="その他事項"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="申請事項/建物の表示"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="申請手続き情報/建物の表示"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="登記の日付"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="順位番号"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="受付年月日"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="受付番号"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="買戻期間"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="付記の日付"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="条件"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="信託原簿番号"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="総代金"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="支払済代金"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="小作の目的"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="承役地"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="要役地"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="地役権図面番号"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="賠償額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="違約金"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="元本利息の支払場所"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="社債の利息"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="延滞損害金"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="利息支払時期"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="抹消物件"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="抵当権の目的"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="利率"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="追加した物件"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="契約期間"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="元本極度額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="確定債権額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="確定元本債権額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="債権極度額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="債権元本極度額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="物件"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="利息及び支払時期"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="共同目的物件"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="延滞税"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="弁済期"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="支払場所"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="優先の定"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="利息支払期"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="延滞税額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="合計債権額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="分割後の債権及び弁済期"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="利息の支払時期"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="根抵当権の目的"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="承役地地役権抹消"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="差押債権"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="清算金の予算額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="根質入債権"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="承継額"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="仮差押債権"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="延滞金"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="利息支払方法"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="元利金支払場所"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="敷金"><xsl:call-template name="General_TAG"/></xsl:template>
	<xsl:template match="抹消すべき登記">
		<!-- 2018-10-01 修正 -->
		<xsl:if test="$移行区分&lt;2 or (count(./受付情報/受付年月日/*[.!=''])&gt;0 or count(./受付情報/受付番号[.!=''])&gt;0)">
			<xsl:call-template name="General_TAG"/>
		</xsl:if>
		<!-- 2018-10-01 修正 EOL -->
	</xsl:template>
	<!--*****************************************************************-->
	<!--##################################################################-->
	<!--### 名義人情報要素                                             ###-->
	<!--##################################################################-->
	<xsl:template name="Name_TAG">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP"><xsl:value-of select="local-name()"/></TD>
					<TD ALIGN="LEFT" WIDTH="75%">
						<xsl:apply-templates/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="権利者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="義務者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="代理人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="所有者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="相続人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="申請人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="所有権登記名義人の表示"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="合併による権利承継者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="所有者の表示"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="代位者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="債務者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="先取特権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="質権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="根質権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="転質権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="抵当権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="根抵当権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="転抵当権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="設定者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="連帯債務者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="受益者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="指定根抵当権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="指定債務者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="被代位者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="登記名義人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="代位申請人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="代位申請人_受益者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="代位申請人_委託者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="共有者及びその持分"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="地上権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="破産管財人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="管財人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="権利承継者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="権利者兼義務者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="債務者兼設定者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="連帯債務者兼設定者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="登記権利者兼登記義務者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="義務者兼申請人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="信託管理人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="申立人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="仮登記権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="買戻権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="登記権利者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="受託者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="委託者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="共有者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="債権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="前所有者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="取得者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="永小作権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="債務者の表示"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="賃借権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="転借権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="採石権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="施行者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="登記義務者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="地役権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="転根抵当権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="債務者及び債務金"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="転根質権者"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="管理人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="申請人兼義務者代理人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="権利者兼代理人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="義務者兼代理人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="権利者代理人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="義務者代理人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<xsl:template match="復代理人"><xsl:call-template name="Name_TAG"/></xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="名義人情報">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						<xsl:if test="not(descendant::被合併会社=null)"><xsl:apply-templates select="被合併会社"/></xsl:if>
						<xsl:if test="not(descendant::被合併法人=null)"><xsl:apply-templates select="被合併法人"/></xsl:if>
						<xsl:if test="not(descendant::被相続人=null)"><xsl:apply-templates select="被相続人"/></xsl:if>
						<!--2014-01-06 CO13-G1-0025 修正-->
						<xsl:if test="not(descendant::被承継者=null)"><xsl:apply-templates select="被承継者"/></xsl:if>
						<!--2014-01-06 CO13-G1-0025 EOL-->
						<xsl:if test="not(descendant::分割会社=null)"><xsl:apply-templates select="分割会社"/></xsl:if>
						<xsl:if test="not(descendant::分割法人=null)"><xsl:apply-templates select="分割法人"/></xsl:if>
						<xsl:if test="not(descendant::住=null)"><xsl:apply-templates select="住"/></xsl:if>
						<xsl:if test="not(descendant::持=null)"><xsl:apply-templates select="持"/></xsl:if>
						<xsl:if test="not(descendant::氏=null)"><xsl:apply-templates select="氏"/></xsl:if>
						<xsl:if test="not(descendant::生=null)"><xsl:apply-templates select="生"/></xsl:if>
						<xsl:if test="not(descendant::取=null)"><xsl:apply-templates select="取"/></xsl:if>
						<xsl:if test="not(descendant::代=null)"><xsl:apply-templates select="代"/></xsl:if>
            			<xsl:if test="not(descendant::電=null)"><xsl:apply-templates select="電"/></xsl:if>
						<xsl:if test="not(descendant::会社法人等番号=null)"><xsl:apply-templates select="会社法人等番号"/></xsl:if>
						<xsl:if test="not(descendant::識別情報提供区分=null)"><xsl:apply-templates select="識別情報提供区分"/></xsl:if>
						<xsl:if test="not(descendant::識別情報未提供理由=null)"><xsl:apply-templates select="識別情報未提供理由"/></xsl:if>
						<xsl:if test="not(descendant::識別情報発行区分=null)"><xsl:apply-templates select="識別情報発行区分"/></xsl:if>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="被合併会社">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						（被合併会社　<xsl:call-template name="print-kanji-image"/>）
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="被合併法人">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						（被合併法人　<xsl:call-template name="print-kanji-image"/>）
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="被相続人">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						（被相続人　<xsl:call-template name="print-kanji-image"/>）
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--2014-01-06 CO13-G1-0025 修正-->
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="被承継者">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						（被承継者　<xsl:call-template name="print-kanji-image"/>）
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<!--2014-01-06 CO13-G1-0025 EOL-->
	<xsl:template match="分割会社">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						（分割会社　<xsl:call-template name="print-kanji-image"/>）
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="分割法人">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						（分割法人　<xsl:call-template name="print-kanji-image"/>）
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="住">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						<xsl:call-template name="print-kanji-image"/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="持">
		<xsl:variable name="Vpos"><xsl:number level="multiple" count="名義人情報" format="1"/></xsl:variable>
		<xsl:if test="not(.='')">
			<xsl:choose>
				<xsl:when test="(name(../../..)='更正後の事項') or (name(../../..)='変更後の事項')">
					<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
						<TR>
							<TD ALIGN="LEFT">
								持分<xsl:call-template name="print-kanji-image"/>
							</TD>
						</TR>
					</TABLE>
				</xsl:when>
				<xsl:otherwise>
					<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
						<TR>
							<TD ALIGN="LEFT">
								<xsl:if test="($Vpos=1)">
									持分<xsl:call-template name="print-kanji-image"/>
								</xsl:if>
								<xsl:if test="not($Vpos=1)">
									<xsl:call-template name="print-kanji-image"/>
								</xsl:if>
							</TD>
						</TR>
					</TABLE>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="氏">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						<xsl:call-template name="print-kanji-image"/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="電">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
            連絡先の電話番号　<xsl:apply-templates/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="生">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						<xsl:apply-templates/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="取">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						（取扱店　<xsl:call-template name="print-kanji-image"/>）
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="代">
		<xsl:if test="not(.='')">
			<xsl:choose>
				<xsl:when test="count(./資格)=0 and count(./代表者氏名)=0">
					<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
						<TR>
							<TD ALIGN="LEFT">
								<xsl:call-template name="print-kanji-image"/>
							</TD>
						</TR>
					</TABLE>
				</xsl:when>
				<xsl:otherwise>
					<xsl:if test="count(./*[(local-name()='資格' or local-name()='代表者氏名' or local-name()='職務執行者等') and .!=''])&gt;0">
						<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
							<TR>
								<TD ALIGN="LEFT">
									<xsl:apply-templates select="資格"/><xsl:apply-templates select="代表者氏名"/><xsl:apply-templates select="職務執行者等"/>
								</TD>
							</TR>
						</TABLE>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="資格">
		<xsl:if test="not(.='')">
			<xsl:call-template name="print-kanji-image"/>　
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="代表者氏名">
		<xsl:if test="not(.='')">
			<xsl:call-template name="print-kanji-image"/>　
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="職務執行者等">
		<xsl:if test="not(.='')">
			<xsl:call-template name="print-kanji-image"/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="会社法人等番号">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						会社法人等番号　<xsl:apply-templates/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="登記識別情報設定">
		<xsl:apply-templates/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="識別情報提供区分">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD WIDTH="50%" ALIGN="LEFT">登記識別情報の提供の有無：</TD>
					<TD WIDTH="50%" ALIGN="LEFT"><xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="識別情報未提供理由">
		<xsl:if test="../識別情報提供区分='無し'">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD WIDTH="5%" ALIGN="LEFT" COLSPAN="2">登記識別情報を提供できない理由：</TD>
				</TR>
				<TR>
					<TD WIDTH="5%" ALIGN="LEFT"></TD>
					<TD WIDTH="95%" ALIGN="LEFT"><SPAN><xsl:call-template name="print-kanji-image"/></SPAN></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="識別情報発行区分">
		<xsl:if test="not(.='無し')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD WIDTH="50%" ALIGN="LEFT">登記識別情報通知希望の有無：</TD>
					<TD WIDTH="50%" ALIGN="LEFT"><xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--##################################################################-->
	<!--### 事項項目要素                                               ###-->
	<!--##################################################################-->
	<xsl:template name="Matter_TAG">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP"><xsl:value-of select="local-name()"/></TD>
					<TD ALIGN="LEFT" WIDTH="75%">
						<xsl:apply-templates/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="変更後の事項"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<xsl:template match="更正後の事項"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<xsl:template match="追加する事項"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<xsl:template match="追加事項"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<xsl:template match="根抵当権の表示"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<xsl:template match="変更更正後の事項"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<xsl:template match="回復すべき登記"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<xsl:template match="受益債権"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<xsl:template match="被担保債権"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<xsl:template match="根質権の表示"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<xsl:template match="回復する登記"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<xsl:template match="回復する登記事項"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<xsl:template match="仮登記根抵当権の表示"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<xsl:template match="保全仮登記"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<xsl:template match="変更の内容"><xsl:call-template name="Matter_TAG"/></xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="事項名">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						<xsl:call-template name="print-kanji-image"/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="事項内容">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						<xsl:apply-templates/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="一般項目">
		<xsl:if test="not(.='')">
			<SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="行">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						<xsl:apply-templates/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--##################################################################-->
	<!--### その他項目要素                                             ###-->
	<!--##################################################################-->
	<xsl:template match="課税価格">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP"><xsl:value-of select="local-name()"/></TD>
					<TD ALIGN="LEFT" WIDTH="75%"><xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="課税価格合計額">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">金
						<xsl:value-of select="format-number(translate(.,',',''),'###,##0')"/> 円
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="課税価格の内訳等">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">内訳　<BR/>
						<SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="移転した持分の課税価格">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">移転持分課税価格　<BR/>
						<SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="登録免許税">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP"><xsl:value-of select="local-name()"/></TD>
					<TD ALIGN="LEFT" WIDTH="75%"><xsl:apply-templates/></TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="登録免許税合計額">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">金
						<xsl:value-of select="format-number(translate(.,',',''),'###,##0')"/> 円
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="登録免許税内訳等">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">内訳　<BR/>
						<SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="登録免許税適用条項">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT">
						<SPAN><xsl:call-template name="print-kanji-image"/></SPAN>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="競売不動産">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="25%" VALIGN="TOP"><xsl:value-of select="local-name()"/></TD>
					<TD ALIGN="LEFT" WIDTH="75%">
						<xsl:apply-templates/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="競売不動産の所在">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">
						<xsl:call-template name="print-kanji-image"/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="競売不動産の地番">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">
						<xsl:call-template name="print-kanji-image"/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="競売不動産の家屋番号">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">
						<xsl:call-template name="print-kanji-image"/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--##################################################################-->
	<!--### 共通要素                                                   ###-->
	<!--##################################################################-->
	<xsl:template match="外字">
		<!-- 2012-06-07 修正 CO11-G1-0026 -->
		<xsl:element name="A">
			<xsl:attribute name="TARGET">_blank</xsl:attribute>
			<xsl:attribute name="HREF"><xsl:value-of select="./ファイル名"/></xsl:attribute>
			<xsl:element name="IMG">
				<xsl:attribute name="SRC"><xsl:value-of select="./ファイル名"/></xsl:attribute>
				<xsl:attribute name="WIDTH">24</xsl:attribute>
				<xsl:attribute name="HEIGHT">24</xsl:attribute>
				<xsl:attribute name="BORDER">0</xsl:attribute>
			</xsl:element>
		</xsl:element>
		<!-- 2012-06-07 修正 CO11-G1-0026 EOL -->
<!--
		<A TARGET="_blank">
			<xsl:attribute name="HREF"><xsl:value-of select="concat(';gaijidirectory;',./ファイル名)"/></xsl:attribute>
			<xsl:value-of select="./ファイル名"/>
		</A>
-->
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="ファイル名">
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="文字コード">
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template name="print-per-line-Ex">
		<xsl:param name="value"/>
		<xsl:choose>
			<xsl:when test="contains($value, '&#xA;')">
				<xsl:value-of select="substring-before($value, '&#xA;')"/>
				<br/>
				<xsl:call-template name="print-per-line-Ex">
					<xsl:with-param name="value" select="substring-after($value, '&#xA;')"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$value"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template name="print-per-line-Ex-gaiji">
		<xsl:for-each select="node()">
		<xsl:choose>
			<xsl:when test="name(.)='外字'">
				<xsl:apply-templates select="."/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="print-per-line-Ex">
					<xsl:with-param name="value" select="."/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
		</xsl:for-each>
	</xsl:template>
	<!-- 2012-06-07 修正 CO11-G1-0026 -->
	<!--*****************************************************************-->
	<xsl:template name="print-kanji-image">
		<xsl:for-each select="node()">
			<xsl:choose>
				<xsl:when test="name(.)='外字'">
					<xsl:apply-templates select="."/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="print-kanji-imagesub">
						<xsl:with-param name="value" select="."/>
					</xsl:call-template>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template name="print-kanji-imagesub">
		<xsl:param name="value"/>
		<xsl:choose>
			<xsl:when test="contains($value, '&#xA;')">
				<xsl:call-template name="Kanji">
					<xsl:with-param name="im0" select="substring-before($value, '&#xA;')"/>
				</xsl:call-template>
				<br/>
				<xsl:call-template name="print-kanji-imagesub">
					<xsl:with-param name="value" select="substring-after($value, '&#xA;')"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="Kanji">
					<xsl:with-param name="im0" select="$value"/>
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template name="Kanji">
		<xsl:param name="im0"/>
		<xsl:value-of select="substring-before($im0, '&amp;')"/>
		<xsl:choose>
			<xsl:when test="substring-after($im0, '&amp;') != '' ">
				<xsl:variable name="im1" select="substring-after($im0, '&amp;')"/>
				<xsl:element name="A">
					<xsl:attribute name="TARGET">_blank</xsl:attribute>
					<xsl:attribute name="HREF"><xsl:value-of select="substring($im1, 1, 6)"/>.bmp</xsl:attribute>
					<xsl:element name="IMG">
						<xsl:attribute name="SRC"><xsl:value-of select="substring($im1, 1, 6)"/>.bmp</xsl:attribute>
						<xsl:attribute name="WIDTH">24</xsl:attribute>
						<xsl:attribute name="HEIGHT">24</xsl:attribute>
						<xsl:attribute name="BORDER">0</xsl:attribute>
					</xsl:element>
				</xsl:element>
				<xsl:call-template name="Kanji">
					<xsl:with-param name="im0" select="substring-after($im1, ';')"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$im0"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<!-- 2012-06-07 修正 CO11-G1-0026 EOL -->
	<!--##################################################################-->
</xsl:stylesheet>
