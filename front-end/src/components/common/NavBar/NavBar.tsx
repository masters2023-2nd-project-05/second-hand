import { ReactNode } from 'react';

import { styled } from 'styled-components';

interface NavBarProps {
  left?: ReactNode;
  center?: ReactNode;
  right?: ReactNode;
  children?: ReactNode;
}

const NavBar = ({ left, center, right, children }: NavBarProps) => {
  return (
    <MyNavBar>
      <MyNavBarTitle>
        <MyLeftTitle>{left}</MyLeftTitle>
        <MyCenter>{center}</MyCenter>
        {right && <MyRightTitle>{right}</MyRightTitle>}
      </MyNavBarTitle>
      {children && <MyChildren>{children}</MyChildren>}
    </MyNavBar>
  );
};

const MyNavBar = styled.div`
  position: sticky;
  top: 0;
  background-color: ${({ theme }) => theme.colors.neutral.backgroundBlur};
  backdrop-filter: blur(3px);
  border-bottom: 1px solid ${({ theme }) => theme.colors.neutral.border};
`;

const MyNavBarTitle = styled.div`
  display: grid;
  align-items: flex-end;
  grid-template-columns: 1fr 1fr 1fr;
  height: 10vh;
  padding: 10px 10px;
  color: ${({ theme }) => theme.colors.neutral.textWeak};
  ${({ theme }) => theme.fonts.body};
  /* border-radius: 10px 10px 0px 0px; */ // TODO: 팝업 애니메이션 적용시 필요함
`;

const MyLeftTitle = styled.p`
  text-align: left;
`;

const MyRightTitle = styled.p`
  text-align: right;
`;

const MyCenter = styled.p`
  font-weight: 600;
  color: ${({ theme }) => theme.colors.neutral.textStrong};
  text-align: center;
`;

const MyChildren = styled.div`
  display: flex;
  justify-content: center;
  padding: 0 10px 10px;
`;

export default NavBar;
