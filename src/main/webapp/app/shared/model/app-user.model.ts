import dayjs from 'dayjs';
import { IAppRole } from 'app/shared/model/app-role.model';

export interface IAppUser {
  id?: number;
  username?: string;
  password?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  lastLogindate?: string | null;
  rol?: IAppRole;
}

export const defaultValue: Readonly<IAppUser> = {};
