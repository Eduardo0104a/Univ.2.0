# ========================================
# Script de Re-despliegue Rápido
# (Reinicia Tomcat para cambios mayores)
# ========================================

Write-Host "`n====================================" -ForegroundColor Cyan
Write-Host "  Re-desplegando con Reinicio" -ForegroundColor Cyan
Write-Host "====================================`n" -ForegroundColor Cyan

$tomcatHome = "C:\apache-tomcat-10.1.12"

# 1. Compilar
Write-Host "[1/5] Compilando proyecto..." -ForegroundColor Yellow
mvnd clean package

if ($LASTEXITCODE -ne 0) {
    Write-Host "`n[ERROR] Fallo en la compilacion" -ForegroundColor Red
    exit 1
}
Write-Host "[OK] Compilacion exitosa`n" -ForegroundColor Green

# 2. Detener Tomcat
Write-Host "[2/5] Deteniendo Tomcat..." -ForegroundColor Yellow
& "$tomcatHome\bin\shutdown.bat" | Out-Null
Start-Sleep -Seconds 3
Write-Host "[OK] Tomcat detenido`n" -ForegroundColor Green

# 3. Limpiar despliegue anterior
Write-Host "[3/5] Limpiando despliegue anterior..." -ForegroundColor Yellow
Remove-Item "$tomcatHome\webapps\ruwana" -Recurse -Force -ErrorAction SilentlyContinue
Remove-Item "$tomcatHome\webapps\ruwana.war" -Force -ErrorAction SilentlyContinue
Write-Host "[OK] Limpieza completada`n" -ForegroundColor Green

# 4. Copiar nuevo WAR
Write-Host "[4/5] Copiando nuevo WAR..." -ForegroundColor Yellow
Copy-Item "target\ruwana.war" "$tomcatHome\webapps\" -Force
Write-Host "[OK] WAR copiado`n" -ForegroundColor Green

# 5. Iniciar Tomcat
Write-Host "[5/5] Iniciando Tomcat..." -ForegroundColor Yellow
Start-Process "$tomcatHome\bin\startup.bat" -WindowStyle Hidden
Start-Sleep -Seconds 5
Write-Host "[OK] Tomcat iniciado`n" -ForegroundColor Green

Write-Host "====================================" -ForegroundColor Cyan
Write-Host "  Re-despliegue completado" -ForegroundColor Green
Write-Host "====================================" -ForegroundColor Cyan
Write-Host "`nAplicacion disponible en:" -ForegroundColor White
Write-Host "  http://localhost:8080/ruwana/`n" -ForegroundColor Cyan
