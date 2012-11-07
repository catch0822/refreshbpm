package refreshbpm

import org.apache.commons.lang.builder.HashCodeBuilder

class SpringUserSpringRole implements Serializable {

	SpringUser springUser
	SpringRole springRole

	boolean equals(other) {
		if (!(other instanceof SpringUserSpringRole)) {
			return false
		}

		other.springUser?.id == springUser?.id &&
			other.springRole?.id == springRole?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (springUser) builder.append(springUser.id)
		if (springRole) builder.append(springRole.id)
		builder.toHashCode()
	}

	static SpringUserSpringRole get(long springUserId, long springRoleId) {
		find 'from SpringUserSpringRole where springUser.id=:springUserId and springRole.id=:springRoleId',
			[springUserId: springUserId, springRoleId: springRoleId]
	}

	static SpringUserSpringRole create(SpringUser springUser, SpringRole springRole, boolean flush = false) {
		new SpringUserSpringRole(springUser: springUser, springRole: springRole).save(flush: flush, insert: true)
	}

	static boolean remove(SpringUser springUser, SpringRole springRole, boolean flush = false) {
		SpringUserSpringRole instance = SpringUserSpringRole.findBySpringUserAndSpringRole(springUser, springRole)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(SpringUser springUser) {
		executeUpdate 'DELETE FROM SpringUserSpringRole WHERE springUser=:springUser', [springUser: springUser]
	}

	static void removeAll(SpringRole springRole) {
		executeUpdate 'DELETE FROM SpringUserSpringRole WHERE springRole=:springRole', [springRole: springRole]
	}

	static mapping = {
		id composite: ['springRole', 'springUser']
		version false
	}
}
