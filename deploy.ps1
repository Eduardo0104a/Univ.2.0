# ========================================
# Script de Despliegue Rápido - Ruwana
# ========================================

Write-Host "`n====================================" -ForegroundColor Cyan
Write-Host "  Desplegando Proyecto Ruwana" -ForegroundColor Cyan
Write-Host "====================================`n" -ForegroundColor Cyan

# 1. Compilar el proyecto
Write-Host "[1/3] Compilando proyecto..." -ForegroundColor Yellow
mvnd clean package

if ($LASTEXITCODE -ne 0) {
    Write-Host "`n[ERROR] Fallo en la compilacion" -ForegroundColor Red
    exit 1
}

Write-Host "[OK] Compilacion exitosa`n" -ForegroundColor Green

# 2. Copiar WAR a Tomcat
Write-Host "[2/3] Copiando WAR a Tomcat..." -ForegroundColor Yellow
$warFile = "target\ruwana.war"
$tomcatWebapps = "C:\apache-tomcat-10.1.12\webapps\"

Copy-Item $warFile $tomcatWebapps -Force
Write-Host "[OK] WAR copiado`n" -ForegroundColor Green

# 3. Esperar redespliegue automático
Write-Host "[3/3] Esperando redespliegue automatico..." -ForegroundColor Yellow
Start-Sleep -Seconds 3
Write-Host "[OK] Listo!`n" -ForegroundColor Green

# Mostrar URLs
Write-Host "====================================" -ForegroundColor Cyan
Write-Host "  Aplicacion desplegada" -ForegroundColor Green
Write-Host "====================================" -ForegroundColor Cyan
Write-Host "`nAccede a tu aplicacion en:" -ForegroundColor White
Write-Host "  http://localhost:8080/ruwana/`n" -ForegroundColor Cyan
Write-Host "API REST disponible en:" -ForegroundColor White
Write-Host "  http://localhost:8080/ruwana/resources/jakartaee10`n" -ForegroundColor Cyan
