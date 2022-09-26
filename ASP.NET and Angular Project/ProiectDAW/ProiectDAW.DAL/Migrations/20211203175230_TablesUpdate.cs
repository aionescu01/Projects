using Microsoft.EntityFrameworkCore.Migrations;

namespace ProiectDAW.DAL.Migrations
{
    public partial class TablesUpdate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ConstructorsSponsors_Constructors_ConstructorName",
                table: "ConstructorsSponsors");

            migrationBuilder.DropForeignKey(
                name: "FK_GrandPrixResults_Drivers_DriverId1",
                table: "GrandPrixResults");

            migrationBuilder.DropForeignKey(
                name: "FK_GrandPrixResults_GrandPrix_GrandPrixId1",
                table: "GrandPrixResults");

            migrationBuilder.DropIndex(
                name: "IX_GrandPrixResults_DriverId1",
                table: "GrandPrixResults");

            migrationBuilder.DropIndex(
                name: "IX_GrandPrixResults_GrandPrixId1",
                table: "GrandPrixResults");

            migrationBuilder.DropColumn(
                name: "DriverId1",
                table: "GrandPrixResults");

            migrationBuilder.DropColumn(
                name: "GrandPrixId1",
                table: "GrandPrixResults");

            migrationBuilder.AlterColumn<string>(
                name: "Name",
                table: "Sponsors",
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
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "DriverId",
                table: "GrandPrixResults",
                type: "int",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "Name",
                table: "GrandPrix",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "Location",
                table: "GrandPrix",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "Country",
                table: "GrandPrix",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "ConstructorName",
                table: "ConstructorsSponsors",
                type: "nvarchar(450)",
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldType: "nvarchar(450)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "Country",
                table: "Constructors",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "BaseLocation",
                table: "Constructors",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "",
                oldClrType: typeof(string),
                oldType: "nvarchar(max)",
                oldNullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_GrandPrixResults_DriverId",
                table: "GrandPrixResults",
                column: "DriverId");

            migrationBuilder.CreateIndex(
                name: "IX_GrandPrixResults_GrandPrixId",
                table: "GrandPrixResults",
                column: "GrandPrixId");

            migrationBuilder.AddForeignKey(
                name: "FK_ConstructorsSponsors_Constructors_ConstructorName",
                table: "ConstructorsSponsors",
                column: "ConstructorName",
                principalTable: "Constructors",
                principalColumn: "Name",
                onDelete: ReferentialAction.Cascade);

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

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ConstructorsSponsors_Constructors_ConstructorName",
                table: "ConstructorsSponsors");

            migrationBuilder.DropForeignKey(
                name: "FK_GrandPrixResults_Drivers_DriverId",
                table: "GrandPrixResults");

            migrationBuilder.DropForeignKey(
                name: "FK_GrandPrixResults_GrandPrix_GrandPrixId",
                table: "GrandPrixResults");

            migrationBuilder.DropIndex(
                name: "IX_GrandPrixResults_DriverId",
                table: "GrandPrixResults");

            migrationBuilder.DropIndex(
                name: "IX_GrandPrixResults_GrandPrixId",
                table: "GrandPrixResults");

            migrationBuilder.AlterColumn<string>(
                name: "Name",
                table: "Sponsors",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.AlterColumn<string>(
                name: "GrandPrixId",
                table: "GrandPrixResults",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int",
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "DriverId",
                table: "GrandPrixResults",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "int",
                oldNullable: true);

            migrationBuilder.AddColumn<int>(
                name: "DriverId1",
                table: "GrandPrixResults",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "GrandPrixId1",
                table: "GrandPrixResults",
                type: "int",
                nullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "Name",
                table: "GrandPrix",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.AlterColumn<string>(
                name: "Location",
                table: "GrandPrix",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.AlterColumn<string>(
                name: "Country",
                table: "GrandPrix",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.AlterColumn<string>(
                name: "ConstructorName",
                table: "ConstructorsSponsors",
                type: "nvarchar(450)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(450)");

            migrationBuilder.AlterColumn<string>(
                name: "Country",
                table: "Constructors",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.AlterColumn<string>(
                name: "BaseLocation",
                table: "Constructors",
                type: "nvarchar(max)",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "nvarchar(max)");

            migrationBuilder.CreateIndex(
                name: "IX_GrandPrixResults_DriverId1",
                table: "GrandPrixResults",
                column: "DriverId1");

            migrationBuilder.CreateIndex(
                name: "IX_GrandPrixResults_GrandPrixId1",
                table: "GrandPrixResults",
                column: "GrandPrixId1");

            migrationBuilder.AddForeignKey(
                name: "FK_ConstructorsSponsors_Constructors_ConstructorName",
                table: "ConstructorsSponsors",
                column: "ConstructorName",
                principalTable: "Constructors",
                principalColumn: "Name",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_GrandPrixResults_Drivers_DriverId1",
                table: "GrandPrixResults",
                column: "DriverId1",
                principalTable: "Drivers",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_GrandPrixResults_GrandPrix_GrandPrixId1",
                table: "GrandPrixResults",
                column: "GrandPrixId1",
                principalTable: "GrandPrix",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
