package org.mclarke.command.ls;

import org.mclarke.os.windows.UserUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LongListFormatter {

    public FileListing format(FileListing fileListing) throws IOException {

        LongListLineBuilder longListLineBuilder = new LongListLineBuilder(fileListing);

        return new FileListing(longListLineBuilder.build(), fileListing.getFile());
    }

    class LongListLineBuilder{

        private Path path;
        private FileListing fileListing;
        private BasicFileAttributes fileAttributes;
        private DateTimeFormatter longListDateFormat = DateTimeFormatter.ofPattern("MMM dd HH:mm");

        public LongListLineBuilder(FileListing fileListing) throws IOException {
            this.fileListing = fileListing;
            this.path = Paths.get(fileListing.getFile().getAbsolutePath());
            this.fileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
        }

        private String formatPermissionLine(boolean canRead, boolean canWrite, boolean canExecute){
            return String.format("%s%s%s", (canRead ? "r" : "-"), (canWrite ? "w" : "-"), (canExecute ? "x" : "-"));
        }

        public String getPermissions(){

            String user = formatPermissionLine(fileListing.getFile().canRead(), fileListing.getFile().canWrite(), fileListing.getFile().canExecute());
            String group = user;
            String other = "---";

            return String.format("%s%s%s%s", fileListing.getFile().isDirectory() ? "d" : "-", user, group, other );
        }

        public String getName() {
            return fileListing.getFileName();
        }

        public String getOwner() throws IOException {

            String ownerName = Files.getOwner(path).getName();
            if (ownerName.contains("\\")) {
                ownerName = ownerName.split("\\\\")[1];
            }
            return ownerName;
        }

        public String getOwnerGroup() {
            return (UserUtils.isCurrentUserAdmin() ? "admin" : "user");
        }

        public String getLastModified() {
            Instant lastModified = fileAttributes.lastModifiedTime().toInstant();
            LocalDateTime ldt = LocalDateTime.ofInstant(lastModified, ZoneId.systemDefault());
            return longListDateFormat.format(ldt);
        }

        public String getSize() {
            return ""+fileAttributes.size();
        }

        public String build() throws IOException {
            return String.format("%10s %7s %7s %10s %12s %30s\n", getPermissions(), getOwner(), getOwnerGroup(), getSize(), getLastModified(), getName());
        }
    }

}
