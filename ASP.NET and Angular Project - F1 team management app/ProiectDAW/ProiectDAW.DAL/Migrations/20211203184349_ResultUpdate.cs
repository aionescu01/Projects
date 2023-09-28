using Microsoft.EntityFrameworkCore.Migrations;

namespace ProiectDAW.DAL.Migrations
{
    public partial class ResultUpdate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_GrandPrixResults_Drivers_DriverId",
                table: "GrandPrixResults");

            migrationBuilder.DropForeignKey(
                name: "FK_GrandPrixResults_GrandPrix_GrandPrixId",
                table: "GrandPrixResults");

            migrationBuilder.AlterColumn<string>(
                name: "Stints",
                table: "GrandPrixResults",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "GrandPrixId",
                table: "GrandPrixResults",
                type: "int",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(int),
                oldType: "int",
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "DriverId",
                table: "GrandPrixResults",
                type: "int",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(int),
                oldType: "int",
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_GrandPrixResults_Drivers_DriverId",
                table: "GrandPrixResults",
                column: "DriverId",
                principalTable: "Drivers",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_GrandPrixResults_GrandPrix_GrandPrixId",
                table: "GrandPrixResults",
                column: "GrandPrixId",
                principalTable: "GrandPrix",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_GrandPrixResults_Drivers_DriverId",
                table: "GrandPrixResults");

            migrationBuilder.DropForeignKey(
                name: "FK_GrandPrixResults_GrandPrix_GrandPrixId",
                table: "GrandPrixResults");

            migrationBuilder.AlterColumn<string>(
                name: "Stints",
                table: "GrandPrixResults",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.AlterColumn<int>(
                name: "GrandPrixId",
                table: "GrandPrixResults",
                type: "int",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<int>(
                name: "DriverId",
                table: "GrandPrixResults",
                type: "int",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AddForeignKey(
                name: "FK_GrandPrixResults_Drivers_DriverId",
                table: "GrandPrixResults",
                column: "DriverId",
                principalTable: "Drivers",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_GrandPrixResults_GrandPrix_GrandPrixId",
                table: "GrandPrixResults",
                column: "GrandPrixId",
                principalTable: "GrandPrix",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
