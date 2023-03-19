package me.epic.spigotlib;

/**
 * The SimpleSemVersion class represents a simple implementation of a semantic versioning object. It has three integer fields representing the major, minor, and patch version numbers.
 */
public class SimpleSemVersion {
    private int major;
    private int minor;
    private int patch;

    /**
     * Constructs a new SimpleSemVersion object with the specified major, minor, and patch version numbers.
     *
     * @param major the major version number
     * @param minor the minor version number
     * @param patch the patch version number
     */
    public SimpleSemVersion(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    /**
     * Creates a SimpleSemVersion object from a version string in the format "major.minor.patch".
     *
     * @param version the version string to parse
     * @return a new SimpleSemVersion object representing the version string
     * @throws IllegalArgumentException if the version string is not in the correct format
     */
    public static SimpleSemVersion fromString(String version) {
        String[] parts = version.split("\\.");

        if (parts.length != 3)
            throw new IllegalArgumentException("String must be in the format major.minor.patch");

        SimpleSemVersion semVersion = null;
        try {
            semVersion = new SimpleSemVersion(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("String must be in the format major.minor.patch");
        }
        return semVersion;
    }

    /**
     * Determines if this SimpleSemVersion object is newer than the specified SimpleSemVersion object.
     *
     * @param other the SimpleSemVersion object to compare to
     * @return true if this object is newer than the other object, false otherwise
     */
    public boolean isNewerThan(SimpleSemVersion other) {
        if (this.major != other.major) {
            return this.major > other.major;
        } else if (this.minor != other.minor) {
            return this.minor > other.minor;
        } else if (this.patch != other.patch) {
            return this.patch > other.patch;
        }
        return false;
    }

    /**
     * Returns the string representation of this SimpleSemVersion object in the format "major.minor.patch".
     *
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return this.major + "." + this.minor + "." + this.patch;
    }
}
