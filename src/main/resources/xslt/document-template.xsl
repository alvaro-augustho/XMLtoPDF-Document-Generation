<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo"
>

    <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>
    <xsl:param name="version" select="'1.0'"/>
    <xsl:template match="treatmentDocumentation">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="my-page">
                    <fo:region-body margin="1in"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="my-page">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block>Description: <xsl:value-of select="description" /></fo:block>
                    <fo:block>Diseases affected by the treatment: <xsl:value-of select="diseases" /></fo:block>
                    <fo:block>Known side effects: <xsl:value-of select="sideEffects" /></fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
