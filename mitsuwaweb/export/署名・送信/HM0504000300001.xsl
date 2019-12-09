<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html"/>
	<!--##################################################################-->
	<xsl:template match="登記申請書">
		<HTML>
			<head>
				<META content="text/html;charset=UTF-8" http-equiv="Content-Type"/>
				<META content="IE=EmulateIE9" http-equiv="X-UA-Compatible"/>
				<title>　</title>
				<style type="text/css">
					BODY{ FONT-FAMILY:"ＭＳ ゴシック"; FONT-SIZE:10pt;}
					TABLE{ FONT-FAMILY:"ＭＳ ゴシック"; FONT-SIZE:10pt;}
					SMALL{ FONT-FAMILY:"ＭＳ ゴシック"; FONT-SIZE:8pt;}
					PRE{ FONT-FAMILY:"ＭＳ ゴシック"; FONT-SIZE:10pt;}
				</style>
			</head>
			<BODY>
				<CENTER>
					<!-- 受領通知埋め込み用タグ -->
					<div id="JuryoTsuchi"/>
					<xsl:apply-templates/>
				</CENTER>
			</BODY>
		</HTML>
	</xsl:template>
	<!--##################################################################-->
	<xsl:template match="申請書区分">
		<TABLE ALIGN="CENTER" BGCOLOR="blue" BORDER="0" CELLPADDING="1" HEIGHT="20" RULES="NONE" WIDTH="100%">
			<TR WIDTH="100%">
				<TD ALIGN="LEFT" WIDTH="100%"/>
			</TR>
		</TABLE>
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
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="経由の有無">
	</xsl:template>
	<!--##################################################################-->
	<xsl:template match="問合書情報">
		<H2>登記識別情報通知・未失効照会</H2>
<P/>
		<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
			<TR>
				<TD ALIGN="LEFT" VALIGN="TOP" WIDTH="100%">登記識別情報が通知され，かつ失効していないことについて照会します。</TD>
			</TR>
		</TABLE>
		<P/>
		<xsl:apply-templates/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="問合書の種類">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="問合書タイトル">
	</xsl:template>
	<!--##################################################################-->
	<xsl:template match="問合手続き情報">
		<xsl:apply-templates select="請求人"/>
		<xsl:apply-templates select="納付額合計"/>
		<xsl:apply-templates select="その他事項"/>
		<xsl:apply-templates select="問合年月日"/>
		<xsl:apply-templates select="宛先登記所"/>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="その他事項">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" VALIGN="TOP" WIDTH="32%">その他事項</TD>
					<TD ALIGN="LEFT" WIDTH="68%">
						<PRE>
<xsl:call-template name="print-kanji-image"/>
</PRE>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="問合年月日">
		<P/>
		<xsl:apply-templates/>
		<P/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="問合年月日/年月日">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="100%">
						<xsl:apply-templates/>照会
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="宛先登記所">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="32%">照会登記所名</TD>
					<TD ALIGN="LEFT" WIDTH="68%">
						<xsl:apply-templates select="/登記申請書/問合書情報/問合手続き情報/宛先登記所/宛先登記所名"/>
					</TD>
				</TR>
				<TR>
					<TD ALIGN="LEFT" WIDTH="32%"/>
						<TD ALIGN="LEFT" WIDTH="68%">登記所コード（
							<xsl:apply-templates select="/登記申請書/問合書情報/問合手続き情報/宛先登記所/登記所コード"/>）
						</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="宛先登記所名">
			<xsl:apply-templates/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="登記所コード">
			<xsl:apply-templates/>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="請求人">
		<xsl:if test="not(./名義人情報/名='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" VALIGN="TOP" WIDTH="32%">
						<xsl:apply-templates select="/登記申請書/問合書情報/問合手続き情報/請求人/名義人種別"/>
					</TD>
					<TD ALIGN="LEFT" WIDTH="68%">
						<xsl:apply-templates select="/登記申請書/問合書情報/問合手続き情報/請求人/名義人情報"/>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="請求人/名義人種別">
		<xsl:if test=".='一般承継人'">
<NOBR>相続人その他の一般承継人</NOBR>
</xsl:if>
		<xsl:if test="not(.='一般承継人')">
<xsl:apply-templates/>
</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="請求人/名義人情報">
		<xsl:variable name="Vpos">
<xsl:number count="名義人情報" format="1" level="multiple"/>
</xsl:variable>
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<xsl:apply-templates/>
			</TABLE>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="請求人/名義人情報/住">
		<TR>
			<TD ALIGN="LEFT" VALIGN="TOP" WIDTH="100%">
				<xsl:call-template name="print-kanji-image"/>
			</TD>
		</TR>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="請求人/名義人情報/氏">
		<TR>
			<TD ALIGN="LEFT" VALIGN="TOP" WIDTH="100%">
				<xsl:call-template name="print-kanji-image"/>
			</TD>
		</TR>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="請求人/名義人情報/代">
		<xsl:if test="not(.='')">
		<TR>
			<TD ALIGN="LEFT" VALIGN="TOP" WIDTH="100%">
				<xsl:call-template name="print-kanji-image"/>
			</TD>
		</TR>
		</xsl:if>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="納付額合計">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
<!-- CO14-G1-0021 -->
				<xsl:attribute name="style">display:none;</xsl:attribute>
<!-- CO14-G1-0021 EOL -->
				<TR>
					<TD ALIGN="LEFT" WIDTH="32%">手数料</TD>
					<TD ALIGN="LEFT" WIDTH="68%">金
						<xsl:value-of select="format-number(translate(.,',',''),'###,##0')"/> 円
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--##################################################################-->
	<xsl:template match="対象物件情報">
		<xsl:variable name="Cpos">
<xsl:number count="対象物件情報" format="1" level="multiple"/>
</xsl:variable>
		<P/>
		<xsl:if test="$Cpos=1">
		<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
			<TR>
				<TD ALIGN="LEFT" VALIGN="TOP" WIDTH="100%">照会する登記識別情報に関する事項</TD>
			</TR>
		</TABLE>
		</xsl:if>
		<xsl:apply-templates/>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="対象物件">
		<xsl:if test="not(./対象物件/物件特定情報/地番区域情報='')">
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="物件指定">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="物件特定情報">
		<xsl:variable name="Cpos">
<xsl:number count="対象物件情報" format="1" level="multiple"/>
</xsl:variable>
		<xsl:if test="not(./地番区域情報='')">
			<HR/>
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="32%">　不動産の所在</TD>
					<TD ALIGN="LEFT" WIDTH="68%">
						<xsl:apply-templates select="物件区分"/>
					</TD>
				</TR>
				<TR>
					<TD ALIGN="LEFT" WIDTH="32%"/>
					<TD ALIGN="LEFT" WIDTH="68%">
						<xsl:apply-templates select="地番区域情報"/>　
						<xsl:apply-templates select="地番家屋番号情報"/>
					</TD>
				</TR>
				<xsl:apply-templates select="物件状態"/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="物件区分">
		<xsl:apply-templates/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="物件状態">
		<TR>
			<TD ALIGN="LEFT" WIDTH="32%">　閉鎖の有無</TD>
			<xsl:if test="not(.='既存')">
				<TD ALIGN="LEFT" WIDTH="68%">
					有　
				</TD>
			</xsl:if>
			<xsl:if test=".='既存'">
				<TD ALIGN="LEFT" WIDTH="68%">
					無
				</TD>
			</xsl:if>
		</TR>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="地番区域情報">
		<xsl:call-template name="print-kanji-image"/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="地番家屋番号情報">
		<xsl:apply-templates/>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="不動産番号">
		<xsl:if test="not(.='')">
			<HR/>
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" WIDTH="32%">　不動産番号</TD>
					<TD ALIGN="LEFT" WIDTH="68%">
						<xsl:apply-templates/>
					</TD>
				</TR>
						<xsl:apply-templates select="../物件特定情報/物件状態"/>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="対象受付番号">
		<xsl:variable name="Cpos">
<xsl:number count="対象受付番号" format="1" level="multiple"/>
</xsl:variable>
		<xsl:if test="not(.='')">
			<BR/>
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR>
					<TD ALIGN="LEFT" COLSPAN="3" WIDTH="32%">
<B> 〔照会番号<xsl:number count="対象受付番号" format="1" level="any"/>〕</B>
</TD>
				</TR>
				<TR>
					<TD ALIGN="LEFT" COLSPAN="1" VALIGN="TOP" WIDTH="32%">　登記の目的</TD>
					<TD ALIGN="LEFT" COLSPAN="2" VALIGN="TOP" WIDTH="68%">
						<xsl:apply-templates select="登記の目的"/>
					</TD>
				</TR>
				<TR>
					<TD ALIGN="LEFT" ROWSPAN="2" VALIGN="TOP" WIDTH="27%">　照会対象登記の受付年月日・<BR/>　受付番号</TD>
          <TD ALIGN="LEFT" VALIGN="TOP" WIDTH="58%">
<xsl:apply-templates select="受付年月日"/>
</TD>
				</TR>
				<TR>
          <xsl:if test="not(./順位番号情報/順位番号='')">
            <TD ALIGN="LEFT" COLSPAN="2" WIDTH="10%">
<xsl:apply-templates select="用紙区分"/>　<xsl:apply-templates select="順位番号情報"/>
</TD>
          </xsl:if>
          <xsl:if test="(./順位番号情報/順位番号='')">
            <TD ALIGN="LEFT" COLSPAN="2" WIDTH="10%">
<xsl:apply-templates select="用紙区分"/>　<xsl:apply-templates select="受付番号情報"/>
</TD>
          </xsl:if>
        </TR>
			</TABLE>
		</xsl:if>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="受付年月日">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="受付年月日/年月日">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>受付
		</xsl:if>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="用紙区分">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="受付番号情報">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="本番">
		<xsl:if test="not(.='')">
			受付第　<xsl:apply-templates/>　
		</xsl:if>
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="枝番">
		<xsl:if test="not(.='')">
			－　<xsl:apply-templates/>
		</xsl:if>　号
	</xsl:template>
	<!--*****************************************************************-->
	<xsl:template match="同順位符号">
		<xsl:if test="not(.='')">
			<xsl:apply-templates/>
		</xsl:if>
	</xsl:template>
	<!--*****************************************************************-->
	<!--<xsl:template match="登記の目的">
		<xsl:if test="not(.='')">
			<TABLE ALIGN="CENTER" BORDER="0" WIDTH="100%">
				<TR WIDTH="100%">
					<TD ALIGN="LEFT" WIDTH="32%" VALIGN="TOP">　登記の目的</TD>
					<TD ALIGN="LEFT" WIDTH="68%">
						<PRE><xsl:apply-templates/></PRE>
					</TD>
				</TR>
			</TABLE>
		</xsl:if>
	</xsl:template>-->
	<xsl:template match="登記の目的">
		<xsl:if test="not(.='')">
			<PRE>
<xsl:call-template name="print-kanji-image"/>
</PRE>
		</xsl:if>
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="/登記申請書/問合書情報/問合手続き情報/請求人/名義人情報" mode="表示">
							<xsl:apply-templates/>
	</xsl:template>
	<!--##################################################################-->
	<xsl:template match="制御情報">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="申請情報">
	</xsl:template>
	<!--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++-->
	<xsl:template match="業務制御情報">
	</xsl:template>
	<!--##################################################################-->
	<xsl:template match="外字">
		<xsl:element name="A">
			<xsl:attribute name="TARGET">_blank</xsl:attribute>
			<xsl:attribute name="HREF">
<xsl:value-of select="./ファイル名"/>
</xsl:attribute>
			<xsl:element name="IMG">
				<xsl:attribute name="SRC">
<xsl:value-of select="./ファイル名"/>
</xsl:attribute>
				<xsl:attribute name="WIDTH">24</xsl:attribute>
				<xsl:attribute name="HEIGHT">24</xsl:attribute>
				<xsl:attribute name="BORDER">0</xsl:attribute>
			</xsl:element>
		</xsl:element>
		<!--<A TARGET="_blank"><xsl:attribute name="HREF"><xsl:value-of select="./ファイル名"/></xsl:attribute><xsl:value-of select="./ファイル名"/></A>-->
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
	<!--##################################################################-->
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
			<xsl:when test="contains($value, '&#10;')">
				<xsl:call-template name="Kanji">
					<xsl:with-param name="im0" select="substring-before($value, '&#10;')"/>
				</xsl:call-template>
				<br/>
				<xsl:call-template name="print-kanji-imagesub">
					<xsl:with-param name="value" select="substring-after($value, '&#10;')"/>
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
					<xsl:attribute name="HREF">
<xsl:value-of select="substring($im1, 1, 6)"/>.bmp</xsl:attribute>
					<xsl:element name="IMG">
						<xsl:attribute name="SRC">
<xsl:value-of select="substring($im1, 1, 6)"/>.bmp</xsl:attribute>
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
	<!--##################################################################-->
</xsl:stylesheet>
